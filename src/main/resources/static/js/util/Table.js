
/**
 * @description table数据
 * @auth sunpeikai
 * @param {string} element 节点
 * @param {string} title 表头标题
 * @param {data.key:head} head 表头
 * @param {data.key:data.value} data 数据
 * @return
 */
function listCreateTable(element,title,head,data) {
    let colspan = Object.keys(head).length;
    let table = $(element);
    let mapper = new Array();
    // 组装表格表头
    let header = "<thead><tr><th style='text-align: center' colspan='" + colspan + "'>" + title + "</th></tr><tr>";
    $.each(head,function (key,value) {
        mapper.push(key);
        header += "<td>" + value + "</td>"
    });
    header += "</tr></thead>"
    // 组装表格body
    let body = "<tbody>";
    $.each(data,function (dataIndex,value) {
        body += "<tr>"
        $.each(mapper,function (mapperIndex,key) {
            body += "<td>" + value[key] + "</td>";
        })
        body += "</tr>";
    })
    table.html(header + body);
}

/**
 * @description table数据
 * @auth sunpeikai
 * @param {string} element 节点
 * @param {string} title 表头标题
 * @param {data.key:head} head 表头
 * @param {data.key:data.value} data 数据
 * @return
 */
function objCreateTable(element,title,head,data) {
    let colspan = Object.keys(head).length;
    let table = $(element);
    let mapper = new Array();
    // 组装表格表头
    let header = "<thead><tr><th style='text-align: center' colspan='" + colspan + "'>" + title + "</th></tr><tr>";
    $.each(head,function (key,value) {
        mapper.push(key);
        header += "<td>" + value + "</td>"
    });
    header += "</tr></thead>"
    // 组装表格body
    let body = "<tbody><tr>";

    $.each(mapper,function (mapperIndex,key) {
        body += "<td>" + data[key] + "</td>";
    })
    body += "</tr>";

    table.html(header + body);
}