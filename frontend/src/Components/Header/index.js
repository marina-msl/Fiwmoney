import profile from '../../Images/Profile.PNG'
import  './style.css'


function Header() {
    return(
        <header className='header'>
            <img className='icon' src={profile} alt='icon'></img>
        </header>
    )
}

export default Header