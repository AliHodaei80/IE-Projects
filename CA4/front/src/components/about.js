import React from 'react';
import "../styles/colors.css";
import "../styles/header.css";
import "../styles/bootstrap.min.css";
import "../styles/bootstrap.min.css"
import "../styles/normalize.css"
import "../styles/font.css"
import "../styles/colors.css"
import "../styles/header.css"
import "../styles/shared.css"
import "../styles/home.css" 
import "../styles/search_result.css"
import "../styles/footer.css"
import table_image from "../images/tables_about.png"
function AboutMizdooni() {
  return (
    <div className="container">
      <div className="row mt-0">
        <div className="col">
          <img
            src={table_image}
            alt="tables_logo"
            className="col img-responsive"
            id="table-image"
          />
        </div>
        <div className="col mt-5 general-text">
          <h3 id="about" className="general-text">
            About Mizdooni
          </h3>
          <span id="about-text">
            Are you tired of waiting in long lines at restaurants or struggling
            to find a table at your favorite eatery? <br />
            <br />
            Look no further than Mizdooni – the ultimate solution for all your
            dining reservation needs.
            <br />
            <br />
            Mizdooni is a user-friendly website where you can reserve a table at
            any restaurant, from anywhere, at a specific time. Whether you're
            craving sushi, Italian, or a quick bite to eat, Mizdooni has you
            covered.
            <br />
            <br />
            With a simple search feature, you can easily find a restaurant based
            on cuisine or location. <br />
            <br />
            <span id="red-text"> The best part? </span> There are no fees for
            making a reservation through Mizdooni. Say goodbye to the hassle of
            calling multiple restaurants or showing up only to find there's a
            long wait. With Mizdooni, you can relax knowing that your table is
            secured and waiting for you. <br />
            <br />
            Don't let dining out be a stressful experience. Visit Mizdooni today
            and start enjoying your favorite meals without the headache of
            making reservations. Reserve your table with ease and dine with
            peace of mind.
          </span>
        </div>
      </div>
    </div>
  );
}

export default AboutMizdooni;
