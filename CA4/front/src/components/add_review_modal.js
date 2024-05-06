import React from "react";
import { useState, useEffect } from "react";
import { postData, sendToast } from "../utils/request_utils.js";
import { useAuth } from "../context/AuthContext";
import { fetchData } from "../utils/request_utils.js";

import InputStarRating from "./input_star_rating.js";
import "bootstrap/dist/js/bootstrap.js";
import "../styles/add_review_modal.css";

function AddReviewModal({ restaurantName, restaurantId }) {
  const [overalRate, setOveralRate] = useState(0);
  const [foodQualityRate, setFoodQualityRate] = useState(0);
  const [serviceRate, setServiceRate] = useState(0);
  const [ambienceRate, setAmbienceRate] = useState(0);
  const [comment, setComment] = useState("");
  const [hadReservationBefore, setHadReservationBefore] = useState(false);
  const { authDetails } = useAuth();

  const handleRespponse = (response) => {
    console.log(response);
    console.log(response.success);
    if (response.success) {
      sendToast(true, response.data);
    } else {
      if ("data" in response) {
        sendToast(false, response.data);
      } else if ("errorMessage" in response) {
        sendToast(false, response.errorMessage);
      }
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    let requestBody = {
      username: authDetails.username,
      ambianceRate: ambienceRate,
      overallRate: overalRate,
      foodRate: foodQualityRate,
      serviceRate: serviceRate,
      comment: comment,
    };
    let res = postData(
      "/restaurants/" + restaurantId + "/feedback",
      requestBody,
      handleRespponse,
      () => {},
      () => {}
    );
  };

  const handleFetchReservations = (response) => {
    console.log(response);
    if (response.success) {
      let reservations = response.data.reservations;
      for (let i in reservations) {
        if (
          reservations[i].restaurantName === restaurantName &&
          new Date() > new Date(reservations[i].datetime)
        ) {
          setHadReservationBefore(true);
          console.log("had reservation begore");
          return;
        }
      }
      setHadReservationBefore(false);
      console.log("no had reservation begore");
    } else {
      console.log("error happend");
    }
  };

  const checkHadReservationBefore = () => {
    if (authDetails.logged_in) {
      fetchData(
        "/reservations/" + authDetails.username,
        null,
        handleFetchReservations,
        (res) => {}
      );
    } else {
      setHadReservationBefore(false);
    }
  };

  checkHadReservationBefore();

  return (
    <>
      {
        <div
          className="modal fade"
          id="addReviewModal"
          role="dialog"
          data-bs-backdrop="static"
          data-bs-keyboard="false"
          tabIndex="-1"
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">
                  Add Review for{" "}
                  <span id="restaurant-name-feedback">{restaurantName}</span>
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>

              <div className="modal-body">
                <form onSubmit={handleSubmit}>
                  <p id="add-review-text">
                    Note: Reviews can only be made by diners who have eaten at
                    this restaurant
                  </p>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="food-rating"
                      className="col-form-label col-md-4"
                    >
                      Food Quality
                    </label>
                    <div className="ms-auto">
                      <InputStarRating
                        setRate={(star) => {
                          setFoodQualityRate(star);
                        }}
                        rate={foodQualityRate}
                      />
                    </div>
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="service-rate"
                      className="col-form-label col-md-4"
                    >
                      Service
                    </label>
                    <div className="ms-auto">
                      <InputStarRating
                        setRate={(star) => {
                          setServiceRate(star);
                        }}
                        rate={serviceRate}
                      />
                    </div>
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="ambience-rate"
                      className="col-form-label col-md-4"
                    >
                      Ambience
                    </label>
                    <div className="ms-auto">
                      <InputStarRating
                        setRate={(star) => {
                          setAmbienceRate(star);
                        }}
                        rate={ambienceRate}
                      />
                    </div>
                  </div>
                  <div className="mb-3 d-flex">
                    <label
                      htmlFor="overal--rate"
                      className="col-form-label col-md-4"
                    >
                      Overall
                    </label>
                    <div className="ms-auto">
                      <InputStarRating
                        setRate={(star) => {
                          setOveralRate(star);
                        }}
                        rate={overalRate}
                      />
                    </div>
                  </div>
                  <div className="mb-3">
                    <label
                      htmlFor="review-comment"
                      className="col-form-label col-md-4"
                    >
                      Comment
                    </label>

                    <textarea
                      className="form-control rounded-4"
                      id="review-comment"
                      placeholder="Type about restaurant..."
                      value={comment}
                      onChange={(e) => setComment(e.target.value)}
                    ></textarea>
                  </div>

                  {!hadReservationBefore && (
                    <div id="no-reservation-before-text">
                      You had no reservation at {restaurantName} Restaurant
                      before.
                    </div>
                  )}

                  <button
                    type="submit"
                    id="add-review-submit-btn"
                    className="btn btn-primary w-100 rounded-4 mt-2"
                    disabled={
                      (comment === "" &&
                        overalRate === 0 &&
                        ambienceRate === 0 &&
                        foodQualityRate === 0 &&
                        serviceRate === 0) ||
                      !hadReservationBefore
                    }
                    data-bs-dismiss="modal"
                  >
                    Submit Review
                  </button>
                  <button
                    type="button"
                    id="close-add-review-btn"
                    class="btn btn-secondary w-100 rounded-4 mt-2"
                    data-bs-dismiss="modal"
                  >
                    Cancel
                  </button>
                </form>
              </div>

              <div className="modal-footer"></div>
            </div>
          </div>
        </div>
      }
    </>
  );
}

export default AddReviewModal;
