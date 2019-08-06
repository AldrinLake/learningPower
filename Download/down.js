
        //创建Ajax对象，不同浏览器有不同的创建方法，其实本函数就是一个简单的new语句而已。  
          function createXMLHttpRequest() {
            var XMLHttpRequest1;
            if (window.XMLHttpRequest) {
              XMLHttpRequest_test = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
              try {
                XMLHttpRequest_test = new ActiveXObject("Msxml2.XMLHTTP");
              } catch (e) {
                XMLHttpRequest_test = new ActiveXObject("Microsoft.XMLHTTP");
              }
            }
            return XMLHttpRequest_test;
          }
 
          function downOneAjax(fileId) {
            //param1与param2就是用户在输入框的两个参数,获取dom节点
            //var userName=document.getElementById("user").value;
            //var psw=document.getElementById("pwd").value;
			document.getElementById(fileId).innerHTML++;//根据id操作节点
            var XMLHttpRequest_test = createXMLHttpRequest();
            //指明相应页面  
            var url = "https://www.aldrinlake.top/LearnForChina/ExcelServlet";
            XMLHttpRequest_test.open("POST", url, true);
            //请求头，保证不乱码  
            XMLHttpRequest_test.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded");
            //对于ajaxRequest，本js.html将会传递param1与param2给你。  
            XMLHttpRequest_test.send("id=" +fileId);
            //对于返回结果怎么处理的问题  
            XMLHttpRequest_test.onreadystatechange = function() {
              //这个4代表已经发送完毕之后  
              if (XMLHttpRequest_test.readyState == 4) {
                //200代表正确收到了返回结果  
                if (XMLHttpRequest_test.status == 200) {
                  //弹出返回结果  
                  //alert(XMLHttpRequest_test.responseText);
                } else {
                  //如果不能正常接受结果，你肯定是断网，或者我的服务器关掉了。  
                  //alert("网络连接中断！");
                }
              }
            };
          }     

