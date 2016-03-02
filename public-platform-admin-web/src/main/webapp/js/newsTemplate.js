//XMLHttpRequest组件
var xhs;
//消息类型菜单的值发生改变时调用该方法,并把消息类型菜单当前的value传递进来
function getType(templateId){
    //当id不大于0时，说明当前选择的是“请选择”这一项，则不做操作
    if(templateId!="0"){
        //请求字符串,把消息类型作为页面参数传到后台
        var url="/newsTemplate/getArticleType.do?templateId="+templateId;
        //创建XMLHttpRequest组件
        xhs=new XMLHttpRequest();
        //设置回调函数,processRequest方法的定义在下面
        xhs.onreadystatechange=processRequest;
        //打开与服务器的地址连接
        xhs.open("get", url, false);
        //发送请求
        xhs.send(null);
    }
}

//processRequest方法作为回调方法
function processRequest(){
    if(xhs.readyState==4){
        if(xhs.status==200) {
            var str = xhs.responseText;
            if (str == "multi_article") {
                $("#media-div").css("display", "none");
                $("#link-div").css("display", "");
            }else if(str == "single_article"){
                $("#media-div").css("display","");
                $("#link-div").css("display","");
            }else{
                $("#media-div").css("display","none");
                $("#link-div").css("display","none");
            }
        }
    }
}

