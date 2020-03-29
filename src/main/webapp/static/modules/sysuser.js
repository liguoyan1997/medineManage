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
        elem: '#LAY-SysUser-table-list'
        , url: '/sysuser/list'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'sysFile', title: '会员头像',width:100,templet:function (d) {
                if(d.sysFile != null){
                    return "<img style='width: 40px;height: 40px;text-align:center;' src='"+d.sysFile+"'>";
                }else{
                    return "暂未上传头像"
                }
            }}
            , {field: 'sysuserLogin', title: '会员账号',minWidth:100}
            , {field: 'sysuserPassWord', title: '会员密码',minWidth:100}
            , {field: 'sysuserName', title: '会员名称',minWidth:100}
            , {field: 'sysuserAge', title: '会员年龄',minWidth:100}
            , {field: 'syscreateTime', title: '注册日期',minWidth:100}
            , {title: '操作', align: 'center',minWidth:190,toolbar: '#table-toolbar'}
        ]]
        , page: true
        , limit: 10
        , height: height_full
        , text: {none:'暂无数据'}
    });


    //监听行工具事件
    table.on('tool(LAY-SysUser-table-list)', function (obj) {
        var id = obj.data.sysid;
        if (obj.event === 'view') {
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title:"用户信息-查看" //不显示标题栏
                , area: ['50%', '80%']
                , shade:0.8
                , content:'/sysuser/view/'+id
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
                , title: '用户信息-编辑'
                , content: '/sysuser/form?sysid=' + id + "&op=" + op
                , maxmin: true
                , shade:0.8
                , area: ['50%', '80%']
                , btn: ['保存',  '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-SysUser-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-SysUser-submit)', function (data) {
                        var field = data.field;
                        admin.req({
                            url: '/sysuser/save'
                            , type: 'post'
                            , data: field
                            , success: function (result) {
                                layer.msg(result.msg, {time: 2000, icon: 6});
                                table.reload('LAY-SysUser-table-list');
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
                url: '/sysuser/delete'
                , type: 'post'
                , data: {sysid: id}
                , success: function (data) {
                    debugger;
                    if(data.code = '0'){
                        layer.msg(data.msg, {time: 2000, icon: 6});
                        table.reload('LAY-SysUser-table-list');
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

    exports('sysuser', {})
});