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
        elem: '#LAY-Medicine-table-list'
        , url: '/medicine/list'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'mid', title: '药品编号',minWidth:100}
            , {field: 'mname', title: '药品名称',minWidth:100}
            , {field: 'mnors', title: '药品规格',minWidth:100}
            , {field: 'marea', title: '药品场地',templet:function(d) {
                return '<span class="layui-btn layui-btn-radius layui-btn layui-btn-warm layui-btn-sm">'+d.marea+'</span>'
                }
            }
            , {field: 'minpri', title: '进价' ,minWidth:100,templet:function(d) {
                return fixed(d.minpri);
                }
            }
            , {field: 'mwpri', title: '批发价' ,minWidth:100,templet:function(d) {
                    return fixed(d.mwpri);
                }
            }
            , {field: 'mpri', title: '零售价' ,minWidth:100,templet:function(d) {
                    return fixed(d.mpri);
                }
            }
            , {field: 'mnum', title: '数量' ,minWidth:100}
            , {field: 'mcon', title: '供应商' ,minWidth:100}
            , {field: 'mcate', title: '类别' ,minWidth:100}
            , {field: 'musetime', title: '有效期至' ,minWidth:100}
            , {title: '操作', align: 'center',minWidth:190,toolbar: '#table-toolbar'}
        ]]
        , page: true
        , limit: 10
        , height: height_full
        , text: {none:'暂无数据'}
    });

    function fixed(str) {
        debugger;
        if (!str) str = '0.00';
        var ret = Math.round(parseFloat(str) * 100) / 100;
        var decimal = ret.toString().split('.');
        if (decimal.length === 1) {
            return ret.toString() + '.00'
        };
        if (decimal.length > 1) {
            if (decimal[1].length < 2) {
                return ret.toString() + '0'
            }
            return ret
        };
        return ret;
    }

    //监听行工具事件
    table.on('tool(LAY-Medicine-table-list)', function (obj) {
        var id = obj.data.mid;
        if (obj.event === 'view') {
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title:"进货信息-查看" //不显示标题栏
                , area: ['50%', '80%']
                , shade:0.8
                , content:'/medicine/view?mid='+id
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
                , title: '进货信息-编辑'
                , content: '/medicine/form?mid=' + id + "&op=" + op
                , maxmin: true
                , shade:0.8
                , area: ['50%', '80%']
                , btn: ['保存',  '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-Medicine-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-Medicine-submit)', function (data) {
                        var field = data.field;
                        admin.req({
                            url: '/medicine/update'
                            , type: 'post'
                            , data: field
                            , success: function (result) {
                                layer.msg(result.msg, {time: 2000, icon: 6});
                                table.reload('LAY-Medicine-table-list');
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
                url: '/medicine/delete'
                , type: 'post'
                , data: {mid: id}
                , success: function (data) {
                    debugger;
                    if(data.code = '0'){
                        layer.msg(data.msg, {time: 2000, icon: 6});
                        table.reload('LAY-Medicine-table-list');
                    }
                }, error: function (ex) {
                    debugger;
                    if(data.code = '-1'){
                        layer.msg('操作失败!!', {time: 2000, icon: 6});
                    }
                }
            });
        }
    });
    exports('medicine', {})
});