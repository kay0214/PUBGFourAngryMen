
/**
 * @description table数据
 * @auth sunpeikai
 * @param {string} element 节点
 * @param {data.key:head} head 表头
 * @param {data.key:data.value} data 数据
 * @return
 */
function listCreateTable(element,head,data) {
    let table = $(element);
    let mapper = new Array();
    // 组装表格表头
    let header = "<thead><tr>";
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
function objectCreateTable(element,head,data) {
    let table = $(element);
    let mapper = new Array();
    // 组装表格表头
    let header = "<thead><tr>";
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