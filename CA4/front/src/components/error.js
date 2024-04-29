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
      <div class="container">
        <div class="error-container">
          <h1 class="error-heading">404</h1>
          <p class="error-message">Error Page</p>
          <a href="#" class="btn btn-primary error-link">
            Go back to Home
          </a>
        </div>
      </div>
      <Footer />
    </main>
  );
}

export default Error;
