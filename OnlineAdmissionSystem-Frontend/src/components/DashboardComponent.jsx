import React, { useEffect, useState } from 'react';
import paymentLogo from '../assets/Payment.png';
import admissionLogo from '../assets/admissions.jpg';
import userLogo from '../assets/icon.jpeg';
import applicationLogo from '../assets/Applications.png'
import { useNavigate, useParams } from 'react-router-dom';

function DashboardComponent() {

    const [showStudentCards, setShowStudentCards] = useState([]);
    const navigator =useNavigate();
    const {email}=useParams();
    // in place of email ,add this
 
    const cardsData = [
        { title: 'User', description: 'Access Your Profile', logo: userLogo ,redirect:``},
        { title: 'Application', description: 'Access Applications', logo: applicationLogo ,redirect:`/AllApplication/satyam@gmail.com`},
        // { title: 'Payments', description: 'Description for Card 5', logo: paymentLogo ,redirect:`/application/1018/payment`},
        // { title: 'Admission', description: 'Description for Card 6', logo: admissionLogo ,redirect:`/admission/1018`},
    ];
 
    useEffect(() => {
        cardsData.forEach((card, index) => {
            setTimeout(() => {
                setShowStudentCards(prevShowStudentCards => [...prevShowStudentCards, index]);
            }, index * 300); // Adjust the delay as needed
        });
    }, []);


  return (
    <div className='dashboarddiv'>
        <h2 style={{textAlign:"center",marginBottom:"100px"}}>Student Dashboard</h2>
        <div className="studentcard-container">
            {cardsData.map((card, index) => (
                <div onClick={()=>{navigator(card.redirect)}} className={`studentcard ${showStudentCards.includes(index) ? 'show' : ''}`} key={index}>
                    <div className="studentcard-logo-container">
                        <img src={card.logo} alt={card.title} className="studentcard-logo" />
                    </div>
                    <h2>{card.title}</h2>
                    <p>{card.description}</p>
                </div>
            ))}
        </div>
</div>
  )
}

export default DashboardComponent