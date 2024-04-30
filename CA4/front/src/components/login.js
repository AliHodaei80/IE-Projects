import React from "react";
import "../styles/colors.css";
import "../styles/header.css";
import "../styles/bootstrap.min.css";
import "../styles/bootstrap.min.css";
import "../styles/normalize.css";
import "../styles/font.css";
import "../styles/colors.css";
import "../styles/header.css";
import "../styles/shared.css";
import "../styles/login_signup.css";
import "../styles/home.css";
import "../styles/search_result.css";
import "../styles/footer.css";
import Header from "./header.js";
import Footer from "./footer.js";
import logo from "../images/logo.png"
export default function Login() {
  return (
    <main>
      <header>
        <Header />
      </header>
      <body>
        <div className="flex-grow-1 wrapper">
          <div className="logo">
            <img src={logo} alt="" />
          </div>
          <div className="text-center mt-4 name">LOGIN</div>
          <form className="p-3 mt-3">
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="userName"
                id="userName"
                placeholder="Username"
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="password"
                name="password"
                id="pwd"
                placeholder="Password"
              />
            </div>
            <button className="btn mt-3">Login</button>
          </form>
          <div className="text-center fs-6">
            <a href="#">Forget password?</a> or <a href="#">Sign up</a>
          </div>
        </div>
      </body>

      <Footer />
    </main>
  );
}
