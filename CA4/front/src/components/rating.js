import starFilledIcon from "../images/icons/star_filled.svg";
import starEmptyIcon from "../images/icons/star_empty.svg";

export default function Rating({ rate }) {
  const filledStars = Math.floor(rate);
  const emptyStars = 5 - filledStars;
  const halfStar = rate - filledStars > 0.5 ? 1 : 0;

  const stars = [];
  for (let i = 0; i < filledStars; i++) {
    stars.push(
      <img
        key={`filled-${i}`}
        className="icon align-middle"
        src={starFilledIcon}
        alt="star_filled"
      />
    );
  }
  if (halfStar) {
    stars.push(
      <img key="half" className="icon" src={starEmptyIcon} alt="star_half" />
    );
  }
  for (let i = 0; i < emptyStars - halfStar; i++) {
    stars.push(
      <img
        key={`empty-${i}`}
        className="icon"
        src={starEmptyIcon}
        alt="star_empty"
      />
    );
  }
  return <div>{stars}</div>;
}
