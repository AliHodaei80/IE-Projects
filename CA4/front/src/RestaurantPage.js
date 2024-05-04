import "react-toastify/dist/ReactToastify.css";
import "./styles/search_result.css";
import React, { useState, useEffect } from "react";
import Header from "./components/header.js";
import RestaurantCard from "./components/restaurant_card.js";
import Footer from "./components/footer.js";
import "./styles/shared.css";
import "./styles/header.css";
import "./styles/restaurant.css";
import "./styles/search_result.css";
import "./styles/footer.css";
import "./styles/normalize.css";
import "./styles/search_result.css";
import {
  Routes,
  Route,
  Link,
  Navigate,
  useNavigate,
  useLocation,
} from "react-router-dom";

export default function RestaurantPage(props) {
  const location = useLocation()
  const state = {};
  console.log("Location restaurant",location);
  return (
    <main class="flex-grow-1">
      <Header></Header>
      <div class="row mt-0 position-relative ms-0">
        <div class="description-box container align-middle">
          <div class="container mt-0">
            <div class="container row">
              <div class="container mb-0 col-sm ms-0 align-middle">
                <img
                  src={state.imageUrl}
                  alt="tables_logo"
                  class="rounded-4 position-relative col rounded-3 mt-5"
                  id="sample-rest-img"
                />
                <div class="container descripton-card ms-0 position-relative">
                  <div class="container d-flex justify-content-between">
                    <p class="display-4 position-absolute mt-5">{state.name}</p>
                    <p
                      class="rounded-4 text-center position-absolute top-50 end-0 me-5"
                      id="rest-status"
                    >
                      Open!
                    </p>
                    <div class="mb-2 pt-4 mt-5 position-absolute top-50">
                      <div class="container">
                        <div class="review-header">
                          <div class="d-flex justify-content-between">
                            <img
                              class="icon p-0"
                              src="../images/icons/clock.svg"
                              alt="star_filled"
                            />
                            <div class="timing p-0">From 11 AM to 10 PM</div>
                          </div>
                          <div class="d-flex align-items-center ms-0">
                            <div class="icon-container text-center">
                              <img
                                class="star-review"
                                src="../images/icons/star_inside_review.png"
                                alt="review"
                              />
                            </div>
                            <div class="rating p-0">160 Reviews</div>
                          </div>
                          <div class="d-flex">
                            <img
                              class="icon"
                              src="../images/icons/fork_knife.svg"
                              alt="star_filled"
                            />
                            <div class="type">Dizy</div>
                          </div>
                        </div>
                        <div class="restaurant-location">
                          <span class="text-muted">
                            Iran, Boshenr, Vali-e-Asr Square
                          </span>
                        </div>
                        <div class="review-content mt-3 p-1">
                          <p>
                            Ali Daei Dizy restaurant is a cultural oasis in the
                            heart of the city, serving up the best of
                            traditional Iranian cuisine.
                            <br />
                            With a menu that boasts a diverse selection of
                            flavorful dishes such as kebabs, stews, and rice
                            dishes, guests will experience the richness and
                            depth of Persian flavors. The ambiance of the
                            restaurant is warm and inviting, with intricate
                            Persian rugs adorning the walls and the soothing
                            sounds of traditional Iranian music playing in the
                            background.
                            <br />
                            Whether you're looking to indulge in a delicious
                            meal with friends or simply craving a taste of Iran,
                            Ali Daei Dizy restaurant is the perfect spot to
                            satisfy your culinary cravings.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="container col mt-5 ms-0 p-1 m-0">
                <h5 id="booking" class="h5">
                  Reserve Table
                </h5>
                <div class="d-inline-flex">
                  <span class="text-center mt-1"> For </span>
                  <div class="input-group text-center">
                    <select
                      class="custom-select count-picker rounded-3 ms-2 form-select"
                      id="inputGroupSelect01"
                      aria-placeholder="Location"
                    >
                      <option selected>2</option>
                      <option value="if">3</option>
                    </select>
                    <span class="text-center mt-1 ms-2"> people, on date </span>
                    <button
                      class="btn date-picker btn-outline-secondary ms-2 search-button"
                      type="button"
                    >
                      <div class="d-flex">
                        <img
                          class="icon p-0"
                          src="../images/icons/calendar.svg"
                          alt="star_filled"
                        />
                        <span class="p-0 ms-3 fw-bolder"> 2024-02-18 </span>
                      </div>
                    </button>
                  </div>
                </div>
                <br />
                <br />
                <span>Available Times for Table #1 (2 seats)</span>
                <div class="container text-center mt-3 ms-0">
                  <div class="row">
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      11:00 AM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      12:00PM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      13:00PM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      14:00PM
                    </button>
                  </div>
                  <div class="row">
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      15:00PM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      18:00PM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      19:00PM
                    </button>
                    <button class="reserve-blob col-sm ms-2 rounded-4 mt-2">
                      20:00PM
                    </button>
                  </div>
                  <div class="general-text w-100 mt-3 mb-3">
                    <p class="fw-bolder red-text">
                      You will reserve this table only for <u>one</u> hour, for
                      more time please contact the restaurant.
                    </p>
                  </div>
                  <div class="row">
                    <button class="red-stylish-button col-sm ms-2 rounded-4">
                      Complete the reservation
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="review-box container w-75 align-middle">
          <div class="container mt-2 p-3">
            <div class="container row">
              <div class="col">
                <div>
                  <h4>What 160 people are saying</h4>
                  <div class="container rounded-4 d-flex justify-content-start">
                    <img
                      class="icon p-0"
                      src="../images/icons/star_filled.svg"
                      alt="star_filled"
                    />
                    <img
                      class="icon p-0"
                      src="../images/icons/star_filled.svg"
                      alt="star_filled"
                    />
                    <img
                      class="icon p-0"
                      src="../images/icons/star_filled.svg"
                      alt="star_filled"
                    />
                    <img
                      class="icon p-0"
                      src="../images/icons/star_filled.svg"
                      alt="star_filled"
                    />
                    <img
                      class="icon p-0"
                      src="../images/icons/star_empty.svg"
                      alt="star_filled"
                    />
                    <p class="text-muted p-0 ms-3">4 based on recent ratings</p>
                  </div>
                </div>
              </div>
              <div class="col">
                <div class="d-flex justify-content-between">
                  <div class="text-center">
                    <p class="mb-0">Food</p>
                    <p class="fw-bolder">4.5</p>
                  </div>
                  <div class="text-center">
                    <p class="mb-0">Service</p>
                    <p class="fw-bolder">4.1</p>
                  </div>
                  <div class="text-center">
                    <p class="mb-0">Ambience</p>
                    <p class="fw-bolder">3.8</p>
                  </div>
                  <div class="text-center">
                    <p class="mb-0">Overall</p>
                    <p class="fw-bolder">4</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="review-pages mt-5 container align-middle">
          <div class="row">
            <div class="col">
              <div class="review-container">
                <div class="container d-flex justify-content-between align-items-center my-3">
                  <div class="rounded-4">
                    <h4>160 Reviews</h4>
                  </div>
                  <button class="add-review-btn rounded-4">Add Review</button>
                </div>

                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Daei
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                  <hr />
                </div>
                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Dari
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                  <hr />
                </div>
                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Daryayei
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                  <hr />
                </div>
                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Daei
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                  <hr />
                </div>
                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Daei
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                  <hr />
                </div>
                <div class="container">
                  <div class="review-header">
                    <div class="profile-photo d-flex">
                      <span class="fw-bolder profile-name general-text position-relative">
                        AD
                      </span>
                    </div>
                    <span class="fw-bolder profile-name general-text review-namer">
                      Ali Daryayei
                    </span>

                    <div>
                      <div class="container rounded-4 d-flex justify-content-start">
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <img
                          class="icon p-0"
                          src="../images/icons/star_filled.svg"
                          alt="star_filled"
                        />
                        <span class="review-date ms-2">
                          Dined on February 17, 2024
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="container ms-5">
                    <div class="fw-bolder">
                      <small>
                        Overall<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Food<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Service<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                      <small class="ms-1">
                        Ambiance<span class="red-text ms-1">5 </span>
                        <span>&#183;</span>
                      </small>
                    </div>
                    <div class="review-content fw-bolder mt-2">
                      <p>
                        Excellent pre-theatre meal. Good food and service. Only
                        small criticism is that music was intrusive.
                      </p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="rounded-4 d-flex fw-bold justify-content-center mt-5">
                <button type="button" class="page-button text-center">
                  <span class="page-no">1</span>
                </button>
                <button type="button" class="page-button text-center ms-3">
                  <span class="page-no">2</span>
                </button>
                <button type="button" class="page-button text-center ms-3">
                  <span class="page-no">3 </span>
                </button>
                <span type="button" class="ms-3 mt-3">
                  <span>&#183;</span>
                  <span>&#183;</span>
                  <span>&#183;</span>
                </span>
                <button type="button" class="page-button text-center ms-3">
                  <span class="page-no">19 </span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer></Footer>
    </main>
  );
}
