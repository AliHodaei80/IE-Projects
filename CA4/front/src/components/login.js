import React, { useState } from "react";
import Header from "./header.js";
import Footer from "./footer.js";
import logo from "../images/logo.png";
import "../styles/login_signup.css";
export default function Login() {
  const [showLogin, setShowLogin] = useState(true);

  return (
    <main>
      <header>
        <Header />
      </header>
      <div
        className="d-flex h-100 w-100 justify-content-center bg-light align-self-center
      position-absolute"
      >
        <div className="w-50 h-50 align-self-center">
          <div className="d-flex justify-content-between">
            <button
              className="btn btn-outline-secondary w-100"
              type="button"
              onClick={() => setShowLogin(false)}
            >
              Signup
            </button>
            <button
              className="btn btn-outline-secondary w-100"
              type="button"
              onClick={() => setShowLogin(true)}
            >
              Login
            </button>
          </div>
          {showLogin ? (
            <div class="container">
              <div class="row justify-content-center">
                <div class="col-md-6 w-75">
                  <div class="mt-4 w-100">
                    <label class="form-label">Username</label>
                    <input
                      type="text"
                      class="form-control rounded-2"
                      name="username"
                      aria-label="Username"
                      aria-describedby="usernameHelp"
                    ></input>
                  </div>
                  <div class="mt-4 w-100">
                    <label class="form-label">Password</label>
                    <input
                      type="password"
                      class="form-control rounded-2"
                      name="password"
                      aria-label="Password"
                      aria-describedby="passwordHelp"
                    ></input>
                  </div>
                </div>
                <button
                  className="btn btn-outline-secondary w-75"
                  type="button"
                >
                  Login
                </button>
              </div>
            </div>
          ) : (
            <div class="container">
              <div class="row justify-content-center">
                <div class="col-md-6 w-75">
                  <div class="mt-4 w-100">
                    <label class="form-label">Username</label>
                    <input
                      type="text"
                      class="form-control rounded-2"
                      name="username"
                      aria-label="Username"
                      aria-describedby="usernameHelp"
                    ></input>
                  </div>
                  <div class="mt-4 w-100">
                    <label class="form-label">Email</label>
                    <input
                      type="email"
                      class="form-control rounded-2"
                      name="email"
                      aria-label="email"
                      aria-describedby="emailHelp"
                    ></input>
                  </div>
                  <div class="mt-4 w-100">
                    <label class="form-label">Country</label>
                    <input
                      type="text"
                      class="form-control rounded-2"
                      name="country"
                      aria-label="country"
                      aria-describedby="countrydHelp"
                    ></input>
                  </div>
                  <div class="mt-4 w-100">
                    <label class="form-label">City</label>
                    <input
                      type="text"
                      class="form-control rounded-2"
                      name="city"
                      aria-label="city"
                      aria-describedby="cityHelp"
                    ></input>
                  </div>
                  <div class="mt-4 w-100">
                    <label class="form-label">Password</label>
                    <input
                      type="text"
                      class="form-control rounded-2"
                      name="password"
                      aria-label="Password"
                      aria-describedby="passwordHelp"
                    ></input>
                  </div>
                  <div class="d-flex mt-4 w-100 justify-content-between">
                    <label class="form-label">I am a new </label>
                    <div>
                      <input
                        class="form-check-input"
                        type="radio"
                        name="exampleRadios"
                        id="exampleRadios1"
                        value="Client"
                        checked
                      ></input>
                      <label class="ms-4 form-label">Client</label>
                    </div>
                    <div>
                      <input
                        class="form-check-input"
                        type="radio"
                        name="exampleRadios"
                        id="exampleRadios1"
                        value="Manager"
                        checked
                      ></input>
                      <label class="ms-4 form-label">Manager</label>
                    </div>
                  </div>
                </div>
                <button
                  className="btn btn-outline-secondary w-75"
                  type="button"
                >
                  Login
                </button>
              </div>
            </div>
          )}
        </div>
      </div>

      <Footer />
    </main>
  );
}
