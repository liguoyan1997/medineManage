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
        elem: '#LAY-SysLog-table-list'
        , url: '/sysLog/list'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'operation', title: '操作',minWidth:100}
            , {field: 'method', title: '请求路径',minWidth:100}
            , {field: 'params', title: '请求参数',minWidth:100}
            , {field: 'ip', title: 'IP',minWidth:100}
            , {field: 'createtime', title: '创建时间',minWidth:100}
            , {field: 'username', title: '操作人',minWidth:100}
        ]]
        , page: true
        , limit: 10
        , height: height_full
        , text: {none:'暂无数据'}
    });


    //监听行工具事件
    table.on('tool(LAY-SysLog-table-list)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'view') {
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title:"角色信息-查看" //不显示标题栏
                , area: ['50%', '80%']
                , shade:0.8
                , content:'/sysLog/view?id='+id
                , btn: '关闭'
                , yes: function (layero) {
                    layer.close(layero);
                }
                , success :function (layero,index) {
                    if(admin.screen()<2) layer.full(index);
                }
            });
        }else if(obj.event === 'del'){
            admin.req({
                url: '/sysLog/delete'
                , type: 'post'
                , data: {id: id}
                , success: function (data) {
                    debugger;
                    if(data.code = '0'){
                        layer.msg(data.msg, {time: 2000, icon: 6});
                        table.reload('LAY-SysLog-table-list');
                    }
                }, error: function (ex) {
                    debugger;
                    if(data.code = '-1'){
                        layer.msg('操作失败', {time: 2000, icon: 6});
                    }
                }
            });
        }
    });

    exports('syslog', {})
});