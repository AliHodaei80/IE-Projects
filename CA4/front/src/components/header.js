import React from "react";

import "../styles/bootstrap.min.css";
import "../styles/normalize.css";
import "../styles/font.css";
import "../styles/colors.css";
// import "../styles/shared.css";
import "../styles/header.css";
import logo from "../images/logo.png";
function Header() {
  return (
    <header className="d-flex sticky-top container-fluid" id="header">
      <img src={logo} alt="logo" className="logo" />
      <span className="header-text d-none d-sm-block">
        Reserve Table From Anywhere!
      </span>
      <div className="welcome-user ms-auto">Welcome, Admin!</div>
      <button className="reserve-button rounded-3 border-0">
        Reserve Now!
      </button>
    </header>
  );
}

export default Header;
