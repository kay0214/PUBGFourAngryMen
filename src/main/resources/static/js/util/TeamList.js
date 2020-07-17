/**
 * 队伍列表组件
 * 【若启用复选框则依赖jQuery】
 *
 * <p>用例: const teamList = new TeamList("teamList", true);
 * <p>载入列表: teamList.load(teamListData);
 * <p>注册勾选回调: teamList.onSelected((a) => {
        console.log(a)
    })
 *
 * @param {string}  listId      列表元素id
 * @param {boolean} checkBox    是否包含复选框
 * @constructor
 */
function TeamList(listId, checkBox = true) {

    /**
     * 玩家定义
     * @typedef {Object}    player
     * @property {number}   teamId
     * @property {string}   name
     * @property {string}   accountId
     */


    /**
     * 全部队伍列表
     * @type {player[]}
     */
    let teamList = [];

    /**
     * 被选中玩家列表定义 <玩家名, 是否被选中>
     * @typedef {Object.<string,boolean>} selectedPlayers
     */

    /**
     * 当前被选中成员列表
     * @type {selectedPlayers}
     */
    const selected = {};

    init(listId);

    /**
     * 勾选回调
     *
     * @callback onSelectedCallback
     * @param {selectedPlayers}    selectedList    当前被选中的列表
     */
    let onSelected = null;

    /**
     * 初始化
     */
    function init(listId) {

    }

    /**
     * 设置勾选回调
     * @param {onSelectedCallback} callback
     */
    this.onSelected = (callback) => {
        onSelected = callback;
    }

    this.test = () => {
        triggerOnSelected([1, 2]);
    }

    /**
     * 触发勾选回调
     * @param selectedList 当前被选中的玩家
     */
    function triggerOnSelected(selectedList) {
        if (onSelected != null && typeof onSelected === "function") {
            onSelected(selectedList);
        }
    }


    /**
     * 载入队伍列表
     * @param {player[]} playerList
     */
    this.load = (playerList) => {
        const teams = {};
        for (let player of playerList) {
            const teamId = player.teamId;
            if (teams[teamId] === undefined) {
                teams[teamId] = [];
            }
            teams[teamId].push(player);
        }

        //构建表格
        const table = document.createElement("table");
        table.classList.add("table-player-list");
        let html = `<tr>
                        <th>队伍</th>
                        <th>
                        玩家
                        ${checkBox ? `<input class="teamListCheckAllPlayer" type='checkbox' checked>` : ""}
                        </th>
                    </tr>`;

        for (let teamId in teams) {
            const teamPlayerCount = teams[teamId].length;
            const teamIdBlock = `<td rowspan="${teamPlayerCount + ""}" class="td-team-id">
                                    <span>${teamId}</span>
                                </td>`;

            let includedTeamId = false;
            const members = teams[teamId];
            for (let player of members) {
                const ele = `<tr data-team-id="${teamId}">
                                ${includedTeamId ? "" : teamIdBlock}
                                <td>
                                    <label>
                                        <div>${player.name}</div>
                                        ${checkBox ? `<input type='checkbox' checked data-name='${player.accountId}'>` : ""}
                                    </label>
                                </td>
                            </tr>`;
                includedTeamId = true;
                html += ele;

                //加入
                selected[player.accountId] = true;
            }
        }
        //填入
        table.innerHTML = html;
        document.getElementById(listId).appendChild(table);

        //注册点击事件
        $(`#${listId} td input[type="checkbox"]`).change((e) => {
            const name = e.currentTarget.getAttribute("data-name");
            const checked = e.currentTarget.checked;

            selected[name] = checked;
            triggerOnSelected(selected);
        });
        //总开关
        $(`#${listId} input.teamListCheckAllPlayer`).change((e) => {
            const checked = e.currentTarget.checked;
            $(`#${listId} td input[type="checkbox"]`).each((e, f) => {
                const name = f.getAttribute("data-name");
                f.checked = checked;
                selected[name] = checked;
            });
            triggerOnSelected(selected);
        });

        //染色
        $(`#${listId} tr`).each((e, f) => {
            const teamId = f.getAttribute("data-team-id");
            if (teamId) {
                const color = mapConstant.color[teamId];
                f.style.backgroundColor = color;
                console.debug(`为队伍${teamId}染色`, color);
            }
        });
    }

    /**
     * 返回当前被勾选玩家
     * @returns {selectedPlayers}
     */
    this.getSelected = () => {
        return selected;
    }

    /**
     * 返回该账号id是否被勾选
     * @param accountId 账号id
     * @returns {boolean}
     */
    this.isSelected = accountId => {
        return selected[accountId] === true;
    }

}