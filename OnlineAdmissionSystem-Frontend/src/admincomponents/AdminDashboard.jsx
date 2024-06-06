import React, { useEffect, useState } from 'react';
import universityLogo from '../assets/University(2).png';
import programLogo from '../assets/Program.png';
import paymentLogo from '../assets/Payment.png';
import admissionLogo from '../assets/admissions.jpg';
import applicationLogo from '../assets/Applications.png';
import { useNavigate } from 'react-router-dom';

function AdminDashboard() {


    const [showCards, setShowCards] = useState([]);
    const navigator =useNavigate();
 
    const cardsData = [
        { title: 'Universities', description: 'Access Universities', logo: universityLogo ,redirect:`/university-list`},
        { title: 'Program Schedule', description: 'Acccess Program Schedule', logo: programLogo ,redirect:`/programschedule` },
        { title: 'Applications', description: 'Acccess Applications Details', logo: applicationLogo ,redirect:`/applications` },
        { title: 'Payments', description: 'Access Payment Details', logo: paymentLogo ,redirect:`/payment`},
        { title: 'Admission', description: 'Access Admission Details', logo: admissionLogo,redirect:`/admission` },
    ];
 
    useEffect(() => {
        cardsData.forEach((card, index) => {
            setTimeout(() => {
                setShowCards(prevShowCards => [...prevShowCards, index]);
            }, index * 300); // Adjust the delay as needed
        });
    }, []);



  return (
    <div className='admindiv'>
         <h2 style={{textAlign:"center",marginBottom:"100px"}}>Admin Dashboard</h2>
    <div className="admincard-container">
            {cardsData.map((card, index) => (
                <div onClick={()=>{navigator(card.redirect)}} className={`admincard ${showCards.includes(index) ? 'show' : ''}`} key={index}>
                    <div className="admincard-logo-container">
                        <img src={card.logo} alt={card.title} className="admincard-logo" />
                    </div>
                    <h2>{card.title}</h2>
                    <p>{card.description}</p>
                </div>
            ))}
        </div>
        </div>
  )
}

export default AdminDashboard