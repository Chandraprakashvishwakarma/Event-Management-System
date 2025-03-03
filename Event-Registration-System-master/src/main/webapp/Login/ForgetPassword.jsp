<%-- 
    Document   : ForgetPassword
    Created on : 02-Mar-2025, 2:50:55 pm
    Author     : chandraprakash
--%>

<script>

    function showVerificationDiv() {

        document.getElementById("verDiv").style.display = "block";
        document.getElementById("verBtn").style.display = "none";
        document.getElementById("verBtn1").style.display = "block";

    }

    function sendOpt() {
        let email = document.getElementById("email").value; // Get email input value

        if (!email) {
            alert("Please enter a valid email address.");
            return;
        }

        fetch("/Event-Registration-System/ForgetPassword", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "email=" + encodeURIComponent(email) // Sending email in request body
        })
                .then(response => response.text()) // Read response
                .then(data => {
                    alert("OTP Sent successfully, It expires in 5 minutes.");
            document.getElementById("verDiv").style.display = "block";
        document.getElementById("verBtn").style.display = "none";
        document.getElementById("verBtn1").style.display = "block";
            
        }) // Show success message
                .catch(error => console.error("Error:", error));
    }

//                                                function verify(){
//                                                    request.set("Verify Button Clicked");
//                                                }
</script>

<%
    Boolean otpSent = (Boolean) request.getAttribute("otpSent");
%>


<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta name="description" content="">
        <meta name="author" content="">

        <title>ERS Sign In</title>
    </head>

    <body>
        <%@include file="/Header/header.jsp" %>

        <section class="preloader">
            <div class="spinner">
                <span class="sk-inner-circle"></span>
            </div>
        </section>

        <main>
            <form method="POST" action="/Event-Registration-System/VerifyOpt" class="register-form">
                <section class="sign-in-form section-padding">
                    <div class="container">
                        <div class="row">

                            <div class="col-lg-8 mx-auto col-12">

                                <h1 class="hero-title text-center mb-5">Forgot the password? No worries..</h1>

                                <div class="div-separator w-50 m-auto my-5"><span></span></div>

                                <div class="row">
                                    <div class="col-lg-8 col-11 mx-auto">
                                        <form role="form" method="POST" action="/Event-Registration-System/VerifyOpt" >

                                            <div class="form-floating mb-4 p-0">
                                                <input type="email" name="email" id="email" pattern="[^ @]*@[^ @]*\.com" class="form-control" placeholder="Email address" maxlength="100" required>

                                                <label for="email">Email address</label>
                                            </div>

                                            <div class="form-floating p-0"  id="verDiv" style="display: none">
                                                <input type="text" name="verification" id="verification" class="form-control" placeholder="Enter Verification code" maxlength="100" >

                                                <label for="verification">Verification code</label>
                                            </div>
                                            <br>

                                            <%
                                                if (request.getParameter("error") != null && request.getParameter("error").equals("true")) {%>
                                            <br>
                                            <p class="text-center" style="color:red;">Invalid email please try again</p>
                                            <%}%>
<!--                                           <button href="${pageContext.request.contextPath}/Login/ForgotPassword.jsp" class="btn custom-btn form-control mt-4 mb-3">
                                                Forget Password
                                            </button>-->
                                            <%
                                                if (request.getParameter("error") != null && request.getParameter("error").equals("wrongOtp")) {%>
                                            <br>
                                            <p class="text-center" style="color:red;">Invalid OPT, please try again</p>
                                            <%}%>
                                            <%
                                                if (request.getParameter("emailNotExist") != null && request.getParameter("emailNotExist").equals("true")) {%>
                                            <br>
                                            <p class="text-center" style="color:red;">Email does not exists, Please enter correct Email.</p>
                                            <%}%>

                                            <a class="btn custom-btn form-control mt-4 mb-3" id="verBtn" onclick="sendOpt();">Send Verification Code</a>
                                            
                                            <button class="btn custom-btn form-control mt-4 mb-3" type="submit" id="verBtn1" style="display: none">Verify</button>
                                            
                                        </form>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </section>
            </form>
        </main>

        <%@include file="/Footer/footer.jsp" %>
    </body>
</html>
