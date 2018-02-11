package controllers
import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc._

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
	def login(name:String, password: String) = Action{
		if(isValidUser(name, password))
			Redirect("/auth/index").withSession(("user", name))
		else
			BadRequest("Invalid username/password")		
	}

	def index() = Action { implicit request =>
		request.session.get("user") match {
			case Some(user) if (user == "Admin") => Ok(s"Welcome $user")
			case Some(user) => BadRequest("Not a valid user")
			case None => BadRequest( "Not logged in. Login please")
		}		
	}

	def isValidUser(name:String, password:String)={
		name == "Admin" && password == "1234"
	}

	def logout(name: String) = Action{implicit request =>
		request.session.get("user") match {
			case Some(user) if user == "Admin" => Ok("Successfully logged out").withNewSession
			case Some(user) => BadRequest("Not a valid user")
			case None => BadRequest("Not logged in")
		}
	}
}
