import React from "react";
import Header from "./header";
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
