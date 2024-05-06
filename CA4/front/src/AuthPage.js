import "react-toastify/dist/ReactToastify.css";
import "./styles/login_signup.css";

import React, { useState, useEffect } from "react";
import Header from "./components/header.js";
import { Routes, Route, Link, Navigate, useNavigate } from "react-router-dom";
import Footer from "./components/footer.js";
import { fetchData, postData, sendToast } from "./utils/request_utils.js";
import { useAuth, AuthProvider } from "./context/AuthContext.js";
const login_path = "/login";
const signup_path = "/signup";
export default function AuthPage() {
  const { authDetails, setAuthDetails } = useAuth();
  const [showLogin, setShowLogin] = useState(true);
  const [userData, setUserData] = useState({});
  const handleChange = (event) => {
    const { name, value } = event.target;
    setUserData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const navigate = useNavigate();
  const [AxiosResult, setResult] = useState({});
  const resolveToast = () => {
    if (AxiosResult.success) {
      sendToast(true, "Signed up / Logged in Succesfully!");
    } else {
      sendToast(false, AxiosResult.errorMessage);
    }
  };

  const onLoginSuccess = (response) => {
    navigate("/home", { replace: true, state: userData });
    const NuserData = userData;
    NuserData.logged_in = true;
    setAuthDetails(NuserData);
  };
  const onSignupSuccess = (response) => {
    navigate("/home", { replace: true, state: userData });
    const NuserData = userData;
    NuserData.logged_in = true;
    setAuthDetails(NuserData);
  };
  const onLoginFailure = (response) => {
    console.log("Login failed not redirecting");
  };
  const onSignupFailure = (response) => {
    console.log("Signup failed not redirecting");
  };

  useEffect(() => {
    if (AxiosResult.hasOwnProperty("success")) {
      resolveToast();
    }
  }, [AxiosResult]);

  return (
    <main>
      <header>
        <Header />
      </header>
      <div
        className="d-flex h-100 w-100 justify-content-center align-self-center
      position-absolute"
      >
        <div className="w-50 h-50 align-self-center">
          <div className="d-flex justify-content-between">
            <button
              className={
                showLogin
                  ? "top-btn w-100 border-bottom-0 border-top-0 border-right-0 border-0  rounded-2"
                  : "top-btn-active w-100 border-bottom-0 border-top-0 border-right-0 border-0 rounded-2 "
              }
              type="button"
              onClick={() => setShowLogin(false)}
            >
              Signup
            </button>
            <button
              className={
                !showLogin
                  ? "top-btn w-100 border-bottom-0 border-top-0 border-right-0 border-0 rounded-2 "
                  : "top-btn-active w-100 border-bottom-0 border-top-0 border-right-0 border-0 rounded-2 "
              }
              type="button"
              onClick={() => setShowLogin(true)}
            >
              Login
            </button>
          </div>
          {showLogin ? (
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-md-6 w-75">
                  <div className="mt-4 w-100">
                    <label className="form-label">Username</label>
                    <input
                      type="text"
                      className="form-control rounded-2 bg-light border-0"
                      onChange={handleChange}
                      name="username"
                      aria-label="Username"
                      aria-describedby="usernameHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100 rounded-2 bg-light border-0">
                    <label className="form-label ">Password</label>
                    <input
                      type="password"
                      onChange={handleChange}
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="password"
                      aria-label="Password"
                      aria-describedby="passwordHelp"
                    ></input>
                  </div>
                </div>
                <button
                  className="btn btn-outline-secondary w-75 mt-4 border-0"
                  onClick={() => {
                    postData(
                      login_path,
                      userData,
                      setResult,
                      onLoginSuccess,
                      onLoginFailure
                    );
                  }}
                  type="button"
                >
                  Login
                </button>
              </div>
            </div>
          ) : (
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-md-6 w-75">
                  <div className="mt-4 w-100 ">
                    <label className="form-label">Username</label>
                    <input
                      type="text"
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="username"
                      onChange={handleChange}
                      aria-label="Username"
                      aria-describedby="usernameHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">Email</label>
                    <input
                      type="email"
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="email"
                      onChange={handleChange}
                      aria-label="email"
                      aria-describedby="emailHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">Country</label>
                    <input
                      type="text"
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="country"
                      onChange={handleChange}
                      aria-label="country"
                      aria-describedby="countrydHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">City</label>
                    <input
                      type="text"
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="city"
                      onChange={handleChange}
                      aria-label="city"
                      aria-describedby="cityHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">Password</label>
                    <input
                      type="password"
                      className="form-control rounded-2 rounded-2 bg-light border-0"
                      name="password"
                      onChange={handleChange}
                      aria-label="Password"
                      aria-describedby="passwordHelp"
                    ></input>
                  </div>
                  <div className="d-flex mt-4 w-100 justify-content-between">
                    <label className="form-label">I am a new </label>
                    <div>
                      <input
                        className="form-check-input"
                        type="radio"
                        onChange={handleChange}
                        name="role"
                        id="radio_1"
                        value="client"
                      ></input>
                      <label className="ms-4 form-label">Client</label>
                    </div>
                    <div>
                      <input
                        className="form-check-input"
                        type="radio"
                        onChange={handleChange}
                        name="role"
                        id="radio_2"
                        value="manager"
                      ></input>
                      <label className="ms-4 form-label">Manager</label>
                    </div>
                  </div>
                </div>
                <button
                  type="button"
                  id="signup_button"
                  className="btn btn-outline-secondary w-100 mt-4 border-0"
                  onClick={() => {
                    console.log("Printing Error message ", AxiosResult);
                    const newUserData = userData;
                    newUserData.address = {};
                    newUserData.address.city = userData.city;
                    newUserData.address.country = userData.country;
                    postData(
                      signup_path,
                      newUserData,
                      setResult,
                      onSignupSuccess,
                      onSignupFailure
                    );
                  }}
                >
                  Register
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
