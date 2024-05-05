import React, { useEffect } from "react";
import "../styles/normalize.css";
import "../styles/font.css";
import "../styles/colors.css";
import "../styles/shared.css";
import "../styles/header.css";
import logo from "../images/logo.png";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { sendToast } from "../utils/request_utils";
const MANAGER = "MANAGER";
function Header() {
  const { authDetails } = useAuth();
  const navigate = useNavigate();
  useEffect(() => {
    console.log("Auth Details", authDetails);
  });
  const handleReserveNow = () => {
    navigate("/authenticate");
  };
  const handleMyRestaurants = () => {
    navigate("/manager_restaurants");
  };
  const handleMyReservations = () => {
    navigate("/reservations");
  };
  let buttonText, buttonOnClick;
  if (authDetails.logged_in === false) {
    buttonText = "Reserve Now!";
    buttonOnClick = handleReserveNow;
  } else if (authDetails.role === MANAGER) {
    buttonText = "My Restaurants";
    buttonOnClick = handleMyRestaurants;
  } else {
    buttonText = "My Reservations";
    buttonOnClick = handleMyReservations;
  }
  const handleNavigateHome = () => {
    navigate("/home");
  };
  return (
    <header
      className="justify-content-between d-flex sticky-top container-fluid"
      id="header"
    >
      <button onClick={handleNavigateHome} className="bg-transparent border-0">
        <div className="d-flex sticky-top container-fluid">
          <img src={logo} alt="logo" className="logo" />
          <span className="mt-4 header-text d-none d-sm-block">
            Reserve Table From Anywhere!
          </span>
        </div>
      </button>
      <div className="d-flex justify-content-between align-items-center">
        {authDetails.logged_in === false ? null : (
          <span className="d-flex align-items-center flex-wrap align-middle w-75">
            {"Welcome  " +
              (authDetails.role === "MANAGER"
                ? "Admin!"
                : authDetails.username)}
          </span>
        )}
        <button
          className="reserve-button rounded-3 border-0"
          id="btn-reserve"
          onClick={buttonOnClick}
        >
          {buttonText}
        </button>
      </div>
    </header>
  );
}

export default Header;
