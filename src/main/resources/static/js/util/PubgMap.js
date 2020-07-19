/**
 * 绘制封装
 *
 * <div id="pubgMapEle"></div>
 *
 * 用例: const pubgMap = new PubgMap("pubgMapEle", 1000, 1000, mapConstant.SANHOK);
 * <p>     pubgMap.drawPosition(0.5, 0.5, 1, 19);
 *
 * @param {string}  elementId   元素id
 * @param {number}  width       宽
 * @param {number}  height      高
 * @param {string}  mapImgSrc   图片url
 * @constructor
 *
 * @author yangy
 */
function PubgMap(elementId, width, height, mapImgSrc = undefined) {

    const that = this;
    let imgElement = null;
    let canvas = null;
    init(elementId, width, height, mapImgSrc);

    /** @type {CanvasRenderingContext2D } */
    const context = canvas.getContext("2d");


    /**
     * 初始化
     */
    function init(parentId, width, height, mapImgSrc) {
        const parent = document.getElementById(parentId);
        //设置canvas
        canvas = that.canvas = document.createElement("canvas");
        canvas.width = width;
        canvas.height = height;
        canvas.style.position = "absolute";
        canvas.style.zIndex = "1";

        //设置图片
        const img = imgElement = document.createElement("img");
        img.style.width = width + "px";
        img.style.height = height + "px";
        img.width = width;
        img.height = height;
        if (mapImgSrc) {
            img.setAttribute("src", mapImgSrc);
        } else if (mapConstant.source.DEFAULT) {
            img.setAttribute("src", mapConstant.source.DEFAULT);
        }
        img.style.position = "absolute";
        img.style.zIndex = "0";

        //设置父元素
        parent.style.position = "relative";
        parent.appendChild(canvas);
        parent.appendChild(img);
    }

    /**
     * 设置背景地图图片
     * @param {string}  mapImgSrc   图片url
     * @param {number}  width       图片长
     * @param {number}  height      图片宽
     */
    this.setBackgroundMap = (mapImgSrc, width = 1000, height = 1000) => {
        imgElement.setAttribute("src", mapImgSrc);
        imgElement.style.width = width + "px";
        imgElement.style.height = height + "px";
        imgElement.width = width;
        imgElement.height = height;
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
        context.fillStyle = "white";
        context.fillText(text, textX, textY);
    }

    /**
     * 绘制文字
     * @param text  文字
     * @param x     横坐标
     * @param y     纵坐标
     * @param color 颜色
     * @param font  大小字体
     */
    function drawText(text, x, y, color = "white", font = "10px Arial") {
        context.font = font;
        context.fillStyle = color;
        context.fillText(text, x, y);
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

    /**
     * 反向实心圆
     * @param x 中心横坐标
     * @param y 中心纵坐标
     * @param radius 大小
     * @param color 颜色
     * @param opacity 透明度
     */
    function fillReverseCircle(x, y, radius, color = "blue", opacity = 0.5) {
        context.fillStyle = color;
        context.beginPath();
        context.globalAlpha = opacity;
        context.arc(x, y, radius, 0, Math.PI * 2);
        context.rect(width, 0, -width, height);
        context.closePath();
        context.fill();
        context.globalAlpha = 1;
    }

    /**
     * 空心圆
     * @param x 中心横坐标
     * @param y 中心纵坐标
     * @param radius 半径
     * @param width 线宽
     * @param color 颜色
     */
    function strokeCircle(x, y, radius, width = 2, color = "white") {
        if (radius <= 0) {
            return;
        }
        context.strokeStyle = color;
        context.lineWidth = width;
        context.beginPath();
        context.arc(x, y, radius, 0, Math.PI * 2);
        context.closePath();
        context.stroke();

    }

    this.test = (x, y) => {
        drawLines(x)
    }

    /**
     * 绘制位置点
     * @param {number}          xRatio        x比率
     * @param {number}          yRatio        y比率
     * @param {number}          groupId       队伍id
     * @param {number|string}   playerIndex   玩家编号
     */
    this.drawPosition = (xRatio, yRatio, groupId, playerIndex) => {
        const pos = convertRatioToPos(xRatio, yRatio);
        drawCircleWithText(pos.x, pos.y, 9, mapConstant.color[groupId], playerIndex + "");
    }

    /**
     *
     * @param {number[][]}  posList       坐标列表
     * @param {number}      lineWidth     线宽
     * @param {string}      color         颜色
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
     * @param {number[][]}  ratioList   比率坐标列表  [[0.1, 0.1], [0.2, 0.2], [0.3, 0.5]]
     * @param {number}      teamIndex   队伍编号
     */
    this.drawPath = (ratioList, teamIndex) => {
        const coordinates = [];
        for (let ratios of ratioList) {
            const pos = convertRatioToPos(ratios[0], ratios[1]);
            coordinates.push([pos.x, pos.y]);
        }
        drawLines(coordinates, 2, mapConstant.color[teamIndex]);
    }

    /**
     * 绘制安全区 篮圈
     * @param xRatio        横坐标比率
     * @param yRatio        纵坐标比率
     * @param radiusRatio   半径比率
     */
    this.drawSafetyZone = (xRatio, yRatio, radiusRatio) => {
        fillReverseCircle(xRatio * width, yRatio * height, radiusRatio * width, "blue", 0.15);
    }

    /**
     * 绘制白圈
     * @param xRatio
     * @param yRatio
     * @param radiusRatio
     */
    this.drawGasWarningZone = (xRatio, yRatio, radiusRatio) => {
        strokeCircle(xRatio * width, yRatio * height, radiusRatio * width);
    }

    /**
     * 显示时间
     * @param instant 时刻
     */
    this.displayTime = (instant) => {
        if (!instant) {
            return;
        }
        const min = parseInt(instant / 60);
        const sec = instant % 60;
        const time = `${min}:${sec}`;
        drawText(time, width / 2, 20);
    }

}