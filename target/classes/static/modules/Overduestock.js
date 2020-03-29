/**
 111

 @author 222
 @version 1.0
 @date 2018-09-27 14:17:47
 */

layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , form = layui.form
        , table = layui.table;

    var height_full;
    if(window.screen.width < 1367){
        height_full  = 'full-180';
    }else{
        height_full  = 'full-220';
    }
    table.render({
        elem: '#LAY-Stock1-table-list'
        , url: '/stock/Overduelist'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'mid', title: '药品编号',minWidth:60}
            , {field: 'mname', title: '药品名称',minWidth:100}
            , {field: 'mnum', title: '数量' ,minWidth:60}
            , {field: 'spid', title: '供应商编号' ,minWidth:100}
            , {field: 'ytime', title: '有效期至' ,minWidth:100}
            , {field: 'stype', title: '药品类别' ,minWidth:100}
            , {
                field: 'overdue', title: '是否过期', templet: function (d) {
                    if (new Date(d.ytime).getTime() > new Date().getTime()) {
                        return '<span class="layui-btn layui-btn-radius layui-btn layui-btn-danger layui-btn-sm">未过期</span>'
                    }else{
                        return '<span class="layui-btn layui-btn-radius layui-btn  layui-btn-sm">已过期</span>'
                    }
                }
            }
            , {title: '操作', align: 'center',minWidth:160,toolbar: '#table-toolbar'}
        ]]
        , page: true
        , limit: 10
        , height: height_full
        , text: {none:'暂无数据'}
    });


    //监听行工具事件
    table.on('tool(LAY-Stock1-table-list)', function (obj) {
        var id = obj.data.id;
        if(obj.event === 'del') {
            admin.req({
                url: '/stock/delete'
                , type: 'post'
                , data: {id: id}
                , success: function (data) {
                    debugger;
                    if (data.code = '0') {
                        layer.msg(data.msg, {time: 2000, icon: 6});
                        table.reload('LAY-Stock1-table-list');
                    }
                }, error: function (ex) {
                    debugger;
                    if (data.code = '-1') {
                        layer.msg('操作失败', {time: 2000, icon: 6});
                    }
                }
            });
        }
    });
    exports('Overduestock', {})
});