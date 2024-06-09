import React, { useState } from "react";
import axios from "axios";
import Header from "./header.js";
import Footer from "./footer.js";
import logo from "../images/logo.png";
import { useNavigate } from "react-router-dom";
import "../styles/login_signup.css";

const signup_path = "/signup";

const Signup = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({
    role: "client",
    username: "",
    password: "",
    email: "",
    country: "",
    city: "",
  });

  const [responseData, setResult] = useState({
    error_message: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  const sendSignup = async () => {
    console.log("Sending signup request", userData);
    const sendingUserData = {
      ...userData,
      address: { city: userData.city, country: userData.country },
    };
    try {
      const response = await axios.post("/api" + signup_path, sendingUserData);

      if (response.data.success) {
        navigate("home");
      } else {
        setResult({ error_message: response.data.data });
      }
    } catch (error) {
      console.error("Error sending signup request:", error);
      setResult({ error_message: error.data.data });
    }
  };

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
                name="username"
                id="userName"
                placeholder="Username"
                value={userData.username}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="email"
                name="email"
                id="email"
                placeholder="Email"
                value={userData.email}
                onChange={handleChange}
              />
            </div>
            <div className="d-flex user-type align-items-center">
              <p className="mb-0 me-4 gender-title">Role:</p>

              <div className="form-check form-check-inline mb-0 me-4">
                <input
                  className="form-check-input"
                  type="radio"
                  name="role"
                  id="customer-type"
                  value="client"
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="customer-type">
                  Client
                </label>
              </div>

              <div className="form-check form-check-inline mb-0 me-4">
                <input
                  className="form-check-input"
                  type="radio"
                  name="role"
                  id="manager-type"
                  value="manager"
                  onChange={handleChange}
                />
                <label className="form-check-label" htmlFor="manager-type">
                  Manager
                </label>
              </div>
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="country"
                id="country"
                placeholder="Country"
                value={userData.country}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="city"
                id="city"
                placeholder="City"
                value={userData.city}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="password"
                name="password"
                id="password"
                placeholder="Password"
                value={userData.password}
                onChange={handleChange}
              />
            </div>
            <button type="button" onClick={sendSignup} className="btn mt-3">
              Signup
            </button>
          </form>
          <div className="text-center fs-6"></div>
          <div className="text-center fs-6">
            Have already an account? <a href="#">Login</a>
          </div>
          <div>
            {responseData.error_message && (
              <div className="text-center text-danger">
                {responseData.error_message}
              </div>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </main>
  );
};

export default Signup;
