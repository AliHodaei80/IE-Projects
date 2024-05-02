import "react-toastify/dist/ReactToastify.css";
import React, { useState } from "react";
import Header from "./header.js";
import Footer from "./footer.js";
import "../styles/login_signup.css";
import { ToastContainer, toast } from "react-toastify";
import { fetchData, postData } from "../utils/request_utils.js";
const base_path = "http://127.0.0.1:8080/";
const login_path = "login";
const signup_path = "signup";

export default function AuthPage() {
  const [showLogin, setShowLogin] = useState(true);
  const [userData, setUserData] = useState({});
  const [AxiosResult, setResult] = useState({});
  const handleChange = (event) => {
    const { name, value } = event.target;
    setUserData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const sendToast = () => {
    const options = {
      position: toast.POSITION.TOP_LEFT,
      autoClose: 8000,
      hideProgressBar: true,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
    };

    if (AxiosResult.success) {
      toast.success("Success Notification!", {
        ...options,
        className: "toast-success",
        bodyClassName: "toast-success-body",
        progressClassName: "toast-success-progress",
      });
    } else {
      toast.error(AxiosResult.errorMessage, {
        ...options,
        className: "toast-error",
        bodyClassName: "toast-error-body",
        progressClassName: "toast-error-progress",
      });
    }
  };

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
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-md-6 w-75">
                  <div className="mt-4 w-100">
                    <label className="form-label">Username</label>
                    <input
                      type="text"
                      className="form-control rounded-2"
                      onChange={handleChange}
                      name="username"
                      aria-label="Username"
                      aria-describedby="usernameHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">Password</label>
                    <input
                      type="password"
                      onChange={handleChange}
                      className="form-control rounded-2"
                      name="password"
                      aria-label="Password"
                      aria-describedby="passwordHelp"
                    ></input>
                  </div>
                </div>
                <button
                  className="btn btn-outline-secondary w-75 mt-4"
                  onClick={async () => {
                    console.log("Printing Error message ", AxiosResult);
                    const result = await postData(
                      base_path + login_path,
                      userData,
                      setResult
                    );
                    sendToast();
                  }}
                  type="button"
                >
                  Login
                </button>
                <ToastContainer />
              </div>
            </div>
          ) : (
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-md-6 w-75">
                  <div className="mt-4 w-100">
                    <label className="form-label">Username</label>
                    <input
                      type="text"
                      className="form-control rounded-2"
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
                      className="form-control rounded-2"
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
                      className="form-control rounded-2"
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
                      className="form-control rounded-2"
                      name="city"
                      onChange={handleChange}
                      aria-label="city"
                      aria-describedby="cityHelp"
                    ></input>
                  </div>
                  <div className="mt-4 w-100">
                    <label className="form-label">Password</label>
                    <input
                      type="text"
                      className="form-control rounded-2"
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
                  className="btn btn-outline-secondary w-75 mt-4"
                  onClick={async () => {
                    console.log("Printing Error message ", AxiosResult);
                    const newUserData = userData;
                    newUserData.address = {};
                    newUserData.address.city = userData.city;
                    newUserData.address.country = userData.country;
                    await postData(
                      base_path + signup_path,
                      newUserData,
                      setResult
                    );
                    sendToast();
                  }}
                >
                  Signup
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
