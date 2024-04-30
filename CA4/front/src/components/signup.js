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
export default function Signup() {
  return (
    <main>
      <header>
        <Header />
      </header>
      <body class="d-flex flex-column">
    <main class="flex-grow-1">
      <div class="wrapper">
        <div class="logo">
          <img src={logo} alt="" />
        </div>
        <div class="text-center mt-4 name">SIGNUP</div>
        <form class="p-3 mt-3">
          <div class="form-field d-flex align-items-center">
            <input
              type="text"
              name="userName"
              id="userName"
              placeholder="Username"
            />
          </div>
          <div class="form-field d-flex align-items-center">
            <input type="email" name="email" id="pwd" placeholder="Email" />
          </div>
          <div class="form-field d-flex align-items-center">
            <input type="text" name="address" id="addr" placeholder="Address" />
          </div>
          <div class="d-flex user-type align-items-center">
            <p class="mb-0 me-4 gender-title">Role:</p>

            <div class="form-check form-check-inline mb-0 me-4">
              <input
                class="form-check-input"
                type="radio"
                name="user-role"
                id="customer-type"
                value="customer"
              />
              <label class="form-check-label" for="customer-type"
                >Customer</label
              >
            </div>

            <div class="form-check form-check-inline mb-0 me-4">
              <input
                class="form-check-input"
                type="radio"
                name="user-role"
                id="manager-type"
                value="manager"
              />
              <label class="form-check-label" for="manager-type">Manager</label>
            </div>
          </div>
          <div class="form-field d-flex align-items-center">
            <input
              type="password"
              name="password"
              id="pwd"
              placeholder="Password"
            />
          </div>
          <button class="btn mt-3">Login</button>
        </form>
        <div class="text-center fs-6">
          Have already an account? <a href="#">Login</a>
        </div>
      </div>
    </main>
  </body> 

      <Footer />
    </main>
  );
}
