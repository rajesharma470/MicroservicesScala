package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc._
import play.twirl.api.Html
import scala.util.{Try, Success, Failure}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
	def index() = Action{
		Ok("Hello World!")
	}
	
	def hello(name:String) = Action{
		val html:Html = views.html.index(name)
		Ok(html)
	}
	
	def sqrt(num: String) = Action{ implicit request =>
		Try(num.toInt) match{
			case Success(ans) if ans >= 0 => Ok(s"Answer is sqrt: ${math.sqrt(ans)}")
			case Success(ans) => BadRequest(s"The input $num is negative number")
			case Failure(ex) => InternalServerError(s"Could not extract the contents from $num") 
		}
	}
}