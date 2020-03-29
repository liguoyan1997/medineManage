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
        elem: '#LAY-Stock-table-list'
        , url: '/stock/list'
        , cols: [[
            {title: '序号',type: 'numbers', fixed: 'left'}
            , {field: 'mid', title: '药品编号',minWidth:60}
            , {field: 'mname', title: '药品名称',minWidth:100}
            , {field: 'mprice', title: '单价',minWidth:60,templet:function(d) {
                    return fixed(d.mprice);
                }
            }
            , {field: 'mnum', title: '数量' ,minWidth:60}
            , {field: 'spid', title: '供应商' ,minWidth:100}
           /* , {field: 'stime', title: '进货时间' ,minWidth:100}*/
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
    table.on('tool(LAY-Stock-table-list)', function (obj) {
        var id = obj.data.id;
        if (obj.event === 'view') {
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title:"库存-查看" //不显示标题栏
                , area: ['50%', '80%']
                , shade:0.8
                , content:'/stock/view?id='+id
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
                , title: '库存-编辑'
                , content: '/stock/form?id=' + id + "&op=" + op
                , maxmin: true
                , shade:0.8
                , area: ['50%', '80%']
                , btn: ['保存',  '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-Stock-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-Stock-submit)', function (data) {
                        var field = data.field;
                        admin.req({
                            url: '/stock/save'
                            , type: 'post'
                            , data: field
                            , success: function (result) {
                                layer.msg(result.msg, {time: 2000, icon: 6});
                                table.reload('LAY-Stock-table-list');
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
        }else if(obj.event === 'sell'){
            var op = 'sell'
            if(new Date(obj.data.ytime).getTime() < new Date().getTime()){
                layer.msg("药品已过期 -- 请销毁 -- 好的呢 ^-^", {time: 2000, icon: 2});
                return false;
            }
            layer.open({
                skin:'layui-layer-molv',
                type: 2
                , title: '药品-出售'
                , content: '/stock/form?id=' + id + "&op=" + op
                , maxmin: true
                , shade:0.8
                , area: ['50%', '50%']
                , btn: ['出售',  '取消']
                , yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index]
                        , submit = layero.find('iframe').contents().find("#LAY-Stock-submit");

                    //监听提交
                    iframeWindow.layui.form.on('submit(LAY-Stock-submit)', function (data) {
                        var field = data.field;
                        admin.req({
                            url: '/stock/sellSave'
                            , type: 'post'
                            , data: field
                            , success: function (result) {
                                layer.msg(result.msg, {time: 2000, icon: 6});
                                table.reload('LAY-Stock-table-list');
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
        }
    });
    exports('stock', {})
});