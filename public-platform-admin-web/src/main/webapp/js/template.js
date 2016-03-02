//XMLHttpRequest组件
var xhs;
//消息类型菜单的值发生改变时调用该方法,并把消息类型菜单当前的value传递进来
function cascade(msgType,accountId){
    //当id不大于0时，说明当前选择的是“请选择”这一项，则不做操作
    if(msgType!="0"){
        //请求字符串,把消息类型作为页面参数传到后台
        var url="/keyword/template.do?msgType="+msgType + "&accountId="+accountId;
        //创建XMLHttpRequest组件
        xhs=new XMLHttpRequest();
        //设置回调函数,processRequest方法的定义在下面
        xhs.onreadystatechange=processRequest;
        //打开与服务器的地址连接
        xhs.open("get", url, true);
        //发送请求
        xhs.send(null);
    }
}

//processRequest方法作为回调方法
function processRequest(){
    if(xhs.readyState==4){
        if(xhs.status==200){
            var str = xhs.responseText;
            //根据返回的字符串为新创建的select节点添加option节点
            var arr1=str.split(",");
            var select = document.getElementById("select_template");
            for(var i=1;i<select.options.length;)
            {
                select.removeChild(select.options[i]);
            }
            if(arr1==""){
                $("#template-div").css("display","");
            }else if(arr1=="="){
                $("#template-div").css("display","none");
            }else{
                $("#template-div").css("display","");
                for(var i=0;i<arr1.length;i++){
                    var arr2=arr1[i].split("=");
                    var child=document.createElement("option");
                    child.innerHTML=arr2[1];
                    child.value=arr2[0];
                    select.appendChild(child);
                }
            }
        }
    }
}
function cascadeBind(msgType,accountId){
    //当id不大于0时，说明当前选择的是“请选择”这一项，则不做操作
    if(msgType!="0"){
        //请求字符串,把消息类型作为页面参数传到后台
        var url="/keyword/template.do?msgType="+msgType + "&accountId="+accountId;
        //创建XMLHttpRequest组件
        xhs=new XMLHttpRequest();
        //设置回调函数,processRequest方法的定义在下面
        xhs.onreadystatechange=processBindRequest;
        //打开与服务器的地址连接
        xhs.open("get", url, true);
        //发送请求
        xhs.send(null);
    }
}
function processBindRequest(){
    if(xhs.readyState==4){
        if(xhs.status==200){
            var str = xhs.responseText;
            //根据返回的字符串为新创建的select节点添加option节点
            var arr1=str.split(",");
            var select_unbind = document.getElementById("select_unbind");
            var select_bind = document.getElementById("select_bind");
            var select_resetbind = document.getElementById("select_resetbind");

            for(var i=1;i<select_unbind.options.length;)
            {
                select_unbind.removeChild(select_unbind.options[i]);
                select_bind.removeChild(select_bind.options[i]);
                select_resetbind.removeChild(select_resetbind.options[i]);
            }
            if(arr1!=""){
                for(var i=0;i<arr1.length;i++){
                    var arr2=arr1[i].split("=");
                    var unbind=document.createElement("option");
                    unbind.innerHTML=arr2[1];
                    unbind.value=arr2[0];
                    var bindchild=document.createElement("option");
                    bindchild.innerHTML=arr2[1];
                    bindchild.value=arr2[0];
                    var resetchild=document.createElement("option");
                    resetchild.innerHTML=arr2[1];
                    resetchild.value=arr2[0];
                    select_unbind.appendChild(unbind);
                    select_bind.appendChild(bindchild);
                    select_resetbind.appendChild(resetchild);
                }
            }

        }
    }
}

function cascadeMsgType(accountId,name){
    //当id不大于0时，说明当前选择的是“请选择”这一项，则不做操作
    if(accountId!="0"){
        //请求字符串,把消息类型作为页面参数传到后台
        var url="/keyword/msgtype.do?accountId="+accountId + "&name=" + name;
        //创建XMLHttpRequest组件
        xhs=new XMLHttpRequest();
        //设置回调函数,processRequest方法的定义在下面
        xhs.onreadystatechange=processMsgTypeRequest;
        //打开与服务器的地址连接
        xhs.open("get", url, true);
        //发送请求
        xhs.send(null);
    }
}

//processRequest方法作为回调方法
function processMsgTypeRequest(){
    if(xhs.readyState==4){
        if(xhs.status==200){
            var str = xhs.responseText;
            //根据返回的字符串为新创建的select节点添加option节点
            var arr1=str.split(",");
            var select = document.getElementById("select_type");
            for(var i=1;i<select.options.length;)
            {
                select.removeChild(select.options[i]);
            }
            for(var i=0;i<arr1.length;i++){
                var arr2=arr1[i].split("=");
                var child=document.createElement("option");
                child.innerHTML=arr2[1];
                child.value=arr2[0];
                select.appendChild(child);
            }
        }
    }
}