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
        elem: '#LAY-SysRole-table-list'
        , url: '/sysRole/list'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'id', title: '角色ID',minWidth:100}
            , {
                field: 'rolename', title: '角色名称', minWidth: 100, templet: function (d) {
                    if (d.rolename == "admin") {
                        return '<span class="layui-btn layui-btn-radius layui-btn layui-btn-sm">' + d.rolename + '</span>'
                    } else {
                        return '<span class="layui-btn layui-btn-radius layui-btn layui-btn-warm layui-btn-sm">'+d.rolename+'</span>'
                    }
                }
            }
            , {field: 'description', title: '角色描述',minWidth:100}
            , {title: '操作', align: 'center',minWidth:190,toolbar: '#table-toolbar'}
        ]]
        , page: true
        , limit: 10
        , height: height_full
        , text: {none:'暂无数据'}
    });


    //监听行工具事件
    table.on('tool(LAY-SysRole-table-list)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'view') {
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title:"角色信息-查看" //不显示标题栏
                , area: ['50%', '80%']
                , shade:0.8
                , content:'/sysRole/view?id='+id
                , btn: '关闭'
                , yes: function (layero) {
                    layer.close(layero);
                }
                , success :function (layero,index) {
                    if(admin.screen()<2) layer.full(index);
                }
            });
        } else if(obj.event === 'edit'){
            var op = 'edit'
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title: '角色信息-编辑'
                , content: '/sysRole/form?id=' + id + "&op=" + op
                , maxmin: true
                , shade:0.8
                , area: ['50%', '80%']
                , btn: ['保存',  '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-SysRole-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-SysRole-submit)', function (data) {
                        var field = data.field;
                        admin.req({
                            url: '/sysRole/save?op='+op
                            , type: 'post'
                            , data: field
                            , success: function (result) {
                                layer.msg(result.msg, {time: 2000, icon: 6});
                                table.reload('LAY-SysRole-table-list');
                            }, error: function (ex) {
                                layer.alert(ex);
                            }
                        });
                        layer.close(index);
                    });

                    submit.trigger('click');
                }, success :function (layero,index) {
                    if(admin.screen()<2) layer.full(index);
                }
            })
        }else if(obj.event === 'del'){
            admin.req({
                url: '/sysRole/delete'
                , type: 'post'
                , data: {id: id}
                , success: function (data) {
                    debugger;
                    if(data.code = '0'){
                        layer.msg(data.msg, {time: 2000, icon: 6});
                        table.reload('LAY-SysRole-table-list');
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

    exports('sysrole', {})
});