<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta name="description" content="">
        <meta name="author" content="">

        <title>Reset Password</title>
    </head>

    <body>
        <%@include file="/Header/header.jsp" %>

        <section class="preloader">
            <div class="spinner">
                <span class="sk-inner-circle"></span>
            </div>
        </section>

        <main>
            <form method="POST" action="/Event-Registration-System/ResetPassword" class="register-form">
                <section class="sign-in-form section-padding">
                    <div class="container">
                        <div class="row">

                            <div class="col-lg-8 mx-auto col-12">

                                <h1 class="hero-title text-center mb-5">Reset Your Password</h1>

                                <div class="div-separator w-50 m-auto my-5"><span></span></div>

                                <div class="row">
                                    <div class="col-lg-8 col-11 mx-auto">
                                        <form role="form" method="POST" action="/Event-Registration-System/ResetPassword">

                                            <div class="form-floating mb-4 p-0">
                                                <input type="email" name="email" id="email" pattern="[^ @]*@[^ @]*\.com" class="form-control" placeholder="Email address" maxlength="100" required>

                                                <label for="email">Email address</label>
                                            </div>

                                            <div class="form-floating p-0">
                                                <input type="password" name="password" id="password" class="form-control" placeholder="Password" maxlength="100" required>

                                                <label for="password">New Password</label>
                                            </div>
                                            <br>

                                            <%
                                                if (request.getParameter("error") != null && request.getParameter("error").equals("true")) {%>
                                            <br>
                                            <p class="text-center" style="color:red;">Invalid email or password, please try again</p>
                                            <%}%>
                                            <%
                                                if (request.getParameter("emailNotExist") != null && request.getParameter("emailNotExist").equals("true")) {%>
                                            <br>
                                            <p class="text-center" style="color:red;">Email does not exists, Please enter correct Email.</p>
                                            <%}%>
                                            <button type="submit" class="btn custom-btn form-control mt-4 mb-3">
                                                Submit
                                            </button>
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
