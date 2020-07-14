/**
 * 绘制封装
 *
 * <div id="pubgMapEle"></div>
 *
 * 用例: const pubgMap = new PubgMap("pubgMapEle", 1000, 1000, mapConstant.SANHOK);
 * <p>     pubgMap.drawPosition(0.5, 0.5, 1, 19);
 *
 * @param {String}  elementId   元素id
 * @param {number}  width       宽
 * @param {number}  height      高
 * @param {String}  mapImgSrc   图片url
 * @constructor
 *
 * @author yangy
 */
function PubgMap(elementId, width, height, mapImgSrc) {

    const that = this;
    init(elementId, width, height, mapImgSrc);
    const canvas = this.canvas;
    /** @type {CanvasRenderingContext2D } */
    const context = canvas.getContext("2d");

    /**
     * 初始化
     */
    function init(parentId, width, height, mapImgSrc) {
        const parent = document.getElementById(parentId);
        //设置canvas
        const canvas = that.canvas = document.createElement("canvas");
        canvas.width = width;
        canvas.height = height;
        canvas.style.position = "absolute";
        canvas.style.zIndex = "1";

        //设置图片
        const img = document.createElement("img");
        img.style.width = width + "px";
        img.style.height = height + "px";
        img.width = width;
        img.height = height;
        img.setAttribute("src", mapImgSrc);
        img.style.position = "absolute";
        img.style.zIndex = "0";

        //设置父元素
        parent.style.position = "relative";
        parent.appendChild(canvas);
        parent.appendChild(img);
    }

    /**
     * 设置背景地图图片
     * @param {String} mapImgSrc 图片url
     */
    this.setBackgroundMap = (mapImgSrc) => {
        const img = document.getElementById(imgId);
        img.setAttribute("src", mapImgSrc);
    }

    /**
     * 清除全部内容
     */
    this.clear = () => {
        canvas.height = canvas.height;
    }

    /**
     * 将比率坐标转换为canvas坐标
     * @param xRatio x占比
     * @param yRatio y占比
     * @returns {{x: number, y: number}}
     */
    function convertRatioToPos(xRatio, yRatio) {
        return {
            x: xRatio * width,
            y: yRatio * height
        }
    }

    /**
     * 画点加文字
     * @param x 横坐标
     * @param y 纵坐标
     * @param size 大小
     * @param color 颜色
     * @param text 文字
     */
    function drawCircleWithText(x, y, size, color = "red", text = "") {
        fillCircle(x, y, size, color);

        const fontSize = size + 5;
        const textX = x - text.length * fontSize * 0.6 / 2;
        const textY = y + size / 2;
        context.font = `${fontSize}px Arial`;
        context.strokeStyle = "white";
        context.fillStyle = "DarkSlateGray";
        context.strokeText(text, textX, textY);
        context.fillText(text, textX, textY);
    }

    /**
     * 实心圆
     * @param x 中心横坐标
     * @param y 中心纵坐标
     * @param size 大小
     * @param color 颜色
     */
    function fillCircle(x, y, size, color = "red") {
        context.fillStyle = color;
        context.beginPath();
        context.arc(x, y, size, 0, Math.PI * 2);
        context.closePath();
        context.fill();
    }

    this.test = (x, y) => {
        drawLines(x)
    }

    /**
     * 绘制位置点
     * @param xRatio        x比率
     * @param yRatio        y比率
     * @param groupId       队伍id
     * @param playerIndex   玩家编号
     */
    this.drawPosition = (xRatio, yRatio, groupId, playerIndex) => {
        const pos = convertRatioToPos(xRatio, yRatio);
        drawCircleWithText(pos.x, pos.y, 9, mapConstant.color[groupId], playerIndex + "");
    }

    /**
     *
     * @param {number[][]}  posList       坐标列表
     * @param {number}      lineWidth     线宽
     * @param {number}      color         颜色
     */
    function drawLines(posList, lineWidth = 1, color = "red") {
        context.lineWidth = lineWidth;
        context.strokeStyle = color;
        context.beginPath();
        const from = {
            x: posList[0][0],
            y: posList[0][1]
        }
        for (let pos of posList) {
            context.moveTo(from.x, from.y);
            context.lineTo(pos[0], pos[1]);
            from.x = pos[0];
            from.y = pos[1];
        }
        context.stroke();
        context.closePath();

    }

    /**
     * 绘制行进路径
     * @param {number[][]}  ratioList   比率坐标列表
     * @param {number}      teamIndex   队伍编号
     */
    this.drawPath = (ratioList, teamIndex) => {
        for (let ratios of ratioList) {
            const pos = convertRatioToPos(ratios[0], ratios[1]);
            ratios[0] = pos.x;
            ratios[1] = pos.y;
        }
        drawLines(ratioList, 2, mapConstant.color[teamIndex]);
    }

}