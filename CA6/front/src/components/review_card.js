import React from "react";
import Rating from "./rating";
export default function ReviewCard({ reviewData }) {
  return (
    <div className="container">
      <div className="d-flex review-header justify-content-between">
        <div className="col-0 d-flex">
          <div className="col-0 profile-photo d-flex">
            <span className="fw-bolder profile-name general-text position-relative">
              AD
            </span>
          </div>
          <span className="fw-bolder profile-name general-text review-namer">
            {reviewData.username}
          </span>
        </div>

        <div className="col-1 me-4">
          <div className="rounded-4 d-flex justify-content-between">
            <Rating rate={reviewData.overallRate} />
            <span className="review-date">
              Dined on {reviewData.submitDateString}
            </span>
          </div>
        </div>
      </div>
      <div className=" ms-5">
        <div className="fw-bolder">
          <small>
            Overall
            <span className="red-text ms-1">{reviewData.overallRate}</span>
            <span>&#183;</span>
          </small>
          <small className="ms-1">
            Food<span className="red-text ms-1">{reviewData.foodRate}</span>
            <span>&#183;</span>
          </small>
          <small className="ms-1">
            Service
            <span className="red-text ms-1">{reviewData.serviceRate}</span>
            <span>&#183;</span>
          </small>
          <small className="ms-1">
            Ambiance
            <span className="red-text ms-1">{reviewData.ambianceRate}</span>
            <span>&#183;</span>
          </small>
        </div>
        <div className="review-content fw-bolder mt-2">
          <p>{reviewData.comment}</p>
        </div>
      </div>
      <hr />
    </div>
  );
}
