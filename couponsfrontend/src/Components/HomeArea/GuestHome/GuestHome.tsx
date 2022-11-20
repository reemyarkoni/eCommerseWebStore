import "./GuestHome.css";
import takeout from "../../../assets/images/HomeArea/GuestHome/takeout.jpg"
import electric from "../../../assets/images/HomeArea/GuestHome/electric.jpg"
import travel from "../../../assets/images/HomeArea/GuestHome/travel.jpg"
import clothes from "../../../assets/images/HomeArea/GuestHome/clothes.jpg"

function GuestHome(): JSX.Element {
    return (
        <div className="GuestHome">
            <div>
                <img src={takeout} width={"100%"} alt="takout image" />
                <h1 className="banner">WELLCOME FRIEND</h1>
                <img src={electric} width={"100%"} alt="electric img" />
                <h1 className="banner"> LOGIN AS EITHER A CUSTOMOER OR A COMPANY</h1>
                <img src={travel} width={"100%"} alt="travel img" />
                <h1 className="banner"> POST YOUR COMPANIES COUPONS IN MANY CATEGORIES</h1>
                <img src={clothes} width={"100%"} alt="clothes img" />
                <h1 className="banner"> EXPLORE COUPONS AND FILTER TO YOUR PREFERENCES</h1>
            </div>
			
        </div>
    );
}

export default GuestHome;
