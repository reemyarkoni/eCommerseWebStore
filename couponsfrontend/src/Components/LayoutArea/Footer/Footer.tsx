import "./Footer.css";
import leftEmblem from "../../../assets/images/Footer/left-emblem.png"
import rightEmblem from "../../../assets/images/Footer/right-emblem.png"

function Footer(): JSX.Element {
    return (
        <div className="Footer">
            <span className="left-emblem"><img src={leftEmblem} alt="grape vine emblem" /></span>
            <h3>NO' 1 COUPON SITE IN THE COUNTRY - ALL RIGHTS RESEREVED TO RE'EM YARKONI</h3>
            <span className="right-emblem"><img src={rightEmblem} alt="grape vine emblem" /></span>
        </div>
    );
}

export default Footer;