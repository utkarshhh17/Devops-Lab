import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { Link } from "react-router-dom";
export default function Nav(){
    
    
    // const location = useLocation();
    // const [bgClass, setBgClass] = useState('');
    // const [showLinks, setShowLinks] = useState(false);
    const [showMenu, setShowMenu]=useState(false)
    const navigate=useNavigate();

    const handleClick=()=>{
      setShowMenu(!showMenu)
    }
 
    
    
    // const toggleLinks = () => {
    //   setShowLinks(!showLinks);
    // };

  
    return(
        <div className={`flex justify-between text-white bg-[#2b2b2b] py-5 `}>

            <div className="flex  small:text-3xl small:basis-1/4 justify-between ml-7 font-oswald text-4xl cursor-pointer"><Link to="/">PicsFlow</Link></div>
            
            

            
            
            
      
        </div>
    )


}