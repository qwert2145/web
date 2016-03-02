//XMLHttpRequest组件
var xhs;
//消息类型菜单的值发生改变时调用该方法,并把消息类型菜单当前的value传递进来
function getActiveId(accountId){
    //当id不大于0时，说明当前选择的是“请选择”这一项，则不做操作
    if(accountId!="0"){
        //请求字符串,把消息类型作为页面参数传到后台
        var url="/couponActive/activeId.do?accountId="+accountId;
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
            var select = document.getElementById("select_activity");
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
