import React from "react";
import Header from "./components/header.js";
import Footer from "./components/footer.js";

// import "./styles/normalize.css";
// import "./styles/bootstrap.min.css";
// import "./styles/header.css";
// import "./styles/font.css";
// import "./styles/footer.css";

export default function ManagerRestaurtns() {
  return (
    <div>
      <Header />
      <main className="flex-grow-1">
        <div className="container-s w-100 text-center">
          <div className="w-100">
            <div className="container"></div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  );
}
