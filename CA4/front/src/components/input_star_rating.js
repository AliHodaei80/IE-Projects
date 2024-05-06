import React from "react";
import starFilledIcon from "../images/icons/star_filled.svg";
import starEmptyIcon from "../images/icons/star_empty.svg";

export default function InputStarRating({ rate, setRate }) {
  const filledStars = Math.floor(rate);
  const emptyStars = 5 - filledStars;

  const handleStarClicked = (number) => {
    console.log("start " + number + " clicked");
    setRate(number);
  };

  const stars = [];
  for (let i = 0; i < filledStars; i++) {
    stars.push(
      <img
        key={`filled-${i}`}
        className="icon align-middle mx-1"
        src={starFilledIcon}
        alt="star_filled"
        onClick={() => handleStarClicked(i + 1)}
      />
    );
  }

  for (let i = 0; i < emptyStars; i++) {
    stars.push(
      <img
        key={`empty-${i}`}
        className="icon mx-1"
        src={starEmptyIcon}
        alt="star_empty"
        onClick={() => handleStarClicked(filledStars + 1 + i)}
      />
    );
  }
  return <div>{stars}</div>;
}
