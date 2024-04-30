import React from "react";
import AboutMizdooni from "./components/about.js";
import SearchBarForm from "./components/searchbar.js";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
import logo_big from "./images/logo_big.png";
import background_image from "./images/home.png";
export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Header />
      <div className="container-s w-100 text-center">
        <div className="home-background w-100" style={{backgroundImage: `url(${background_image})`}}>
          <div className="container">
            <div className="input-group mb-3">
              <img className="big-logo" src={logo_big} />
            </div>
            <SearchBarForm />
          </div>
        </div>
      </div>
      <AboutMizdooni />
      <Footer />
    </main>
  );
}
