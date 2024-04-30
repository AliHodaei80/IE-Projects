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
import axios from "axios";
import logo from "../images/logo.png";
const send_signup = async () => {
  console.log("Sending signup request");
  axios({
    method: "post",
    url: "http://127.0.0.1:8080/signup",
    headers: { "content-type": "application/json" },
    data: {
      role: "client",
      username: "user2",
      password: "1234",
      email: "user1@gmail.com",
      address: {
        country: "Iran",
        city: "Tehran",
      },
    },
  });
};

export default function Signup() {
  return (
    <main>
      <header>
        <Header />
      </header>
      <div className="d-flex flex-column flex-grow-1">
        <div className="wrapper">
          <div className="logo">
            <img src={logo} alt="" />
          </div>
          <div className="text-center mt-4 name">SIGNUP</div>
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
              <input type="email" name="email" id="pwd" placeholder="Email" />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="address"
                id="addr"
                placeholder="Address"
              />
            </div>
            <div className="d-flex user-type align-items-center">
              <p className="mb-0 me-4 gender-title">Role:</p>

              <div className="form-check form-check-inline mb-0 me-4">
                <input
                  className="form-check-input"
                  type="radio"
                  name="user-role"
                  id="customer-type"
                  value="customer"
                />
                <label className="form-check-label" for="customer-type">
                  Customer
                </label>
              </div>

              <div className="form-check form-check-inline mb-0 me-4">
                <input
                  className="form-check-input"
                  type="radio"
                  name="user-role"
                  id="manager-type"
                  value="manager"
                />
                <label className="form-check-label" for="manager-type">
                  Manager
                </label>
              </div>
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="password"
                name="password"
                id="pwd"
                placeholder="Password"
              />
            </div>
            <button onClick={send_signup()} className="btn mt-3">
              Signup
            </button>
          </form>
          <div className="text-center fs-6">
            Have already an account? <a href="#">Login</a>
          </div>
        </div>
      </div>
      <Footer />
    </main>
  );
}
