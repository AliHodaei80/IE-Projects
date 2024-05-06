import React from "react";
import { useState } from "react";
import { postData, sendToast } from "../utils/request_utils.js";
import "bootstrap/dist/js/bootstrap.js";
import "../styles/add_table_modal.css";
import "../styles/reservation_info_modal.css";
import { useAuth } from "../context/AuthContext";

function ReservationInfoModal({ tableNumber, restAddress, reserveDateTime }) {
  return (
    <>
      {
        <div
          className="modal fade"
          //   role="dialog"
          id="reservationInfoModal"
          //   data-bs-backdrop="static"
          //   data-bs-keyboard="false"
          //   tabIndex="-1"
          //   aria-labelledby="staticBackdropLabel"
          //   aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header align-items-center">
                <h1 className="modal-title fs-5">Reservation Detail</h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body">
                {/* <form onSubmit={handleSubmit}> */}
                <div className="mb-3 d-flex">
                  <label className="col-form-label col-md-4">
                    Table Number
                  </label>

                  <div className="col-form-label ms-auto col-md-4">
                    {tableNumber}
                  </div>
                </div>
                <div className="mb-3 d-flex">
                  <label className="col-form-label col-md-4">Time</label>

                  <div className="col-form-label ms-auto col-md-4">
                    {new Date(reserveDateTime).toLocaleString()}
                  </div>
                </div>
                <div className="mb-3 d-flex flex-column">
                  <label className="col-form-label col-md-4">Address</label>

                  <div className="col-form-label col-md-4 ms-auton">
                    {restAddress.country}, {restAddress.city},{" "}
                    {restAddress.street}
                  </div>
                </div>

                <button
                  type="button"
                  id="close-reservation-info-btn"
                  className="btn btn-secondary w-100 rounded-4 mt-2"
                  data-bs-dismiss="modal"
                >
                  Close
                </button>
                {/* </form> */}
              </div>
              <div className="modal-footer"></div>
            </div>
          </div>
        </div>
      }
    </>
  );
}

export default ReservationInfoModal;
