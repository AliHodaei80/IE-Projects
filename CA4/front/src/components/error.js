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
import "../styles/home.css";
import "../styles/search_result.css";
import "../styles/footer.css";
import Header from "./header"
import Footer from "./footer";
function Error() {
  return (
    <main>
      <Header />
      <div className="container">
        <div className="error-container">
          <h1 className="error-heading">404</h1>
          <p className="error-message">Error Page</p>
          <a href="#" className="btn btn-primary error-link">
            Go back to Home
          </a>
        </div>
      </div>
      <Footer />
    </main>
  );
}

export default Error;
