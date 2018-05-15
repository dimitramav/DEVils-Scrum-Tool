<template>
	<b-container class="Title" style="background-color:#444545;" fluid>
	    <b-row>
	    	<b-col>
  				<b-img src="https://static1.squarespace.com/static/538df61ae4b055cc3310e47f/t/59124725893fc098b54c30a7/1494370113194/vassardevil.jpg?format=500w" width="55" height="55" fluid alt="Responsive image"/>
	    	</b-col>
	    	<b-col cols="8">
	    		<h1 align="center" style="font-family:Courier New;color:white;"> Scrum Tool </h1>
    		</b-col>
	    	<b-col>
	    		<button type="button" class="btn btn-warning" v-on:click="gotoSignIn">Log In</button>
	        	<button type="button" class="btn btn-warning">Sign Up</button>
	   		</b-col>
	   	</b-row>
		<b-row align-h="center">
	    	<p style="font-family:Courier New; color:white; font-style: italic;text-decoration: overline-underline;" align="center">Let's get agile together! </p>
	   	</b-row>
	    <b-row align-h="center">
	        <ul>
	            <li><a class="active" href="#home" >Home</a></li>
	            <li><a href="#features">Features</a></li>
	            <li><a href="#about">About Us</a></li>
	        </ul>
	    </b-row>

        <div id="home" class="Home">
	      <img src="../assets/ScrumWordCloud.jpg" class="wordcloud"">
	      <button type="button" class="btn btn-warning">Join Free Now</button>
	      <p><br/></p>
	    </div>

	    <b-container class="Features" id="features" style="background-color: #ffffff;" fluid>
			<b-row align-h="center">
				<h2 style="font-family:Courier New;"> Features </h2>
			</b-row>
			<b-row>
				<p></p>
			</b-row>
			<b-row>
			<b-col>
				<b-list-group style="text-align:center; font-style: italic;font-family: Comic Sans MS, cursive, sans-serif;">
				  <b-list-group-item>Implement agile / scrum methodology</b-list-group-item>
				  <b-list-group-item>Free of charge forever</b-list-group-item>
				  <b-list-group-item>Variety of tools to choose by</b-list-group-item>
				  <b-list-group-item>Create , participate , communicate in projects</b-list-group-item>
				  <b-list-group-item>Update Product and Sprint Backlogs</b-list-group-item>
				  <b-list-group-item>Manage your sprints and deadlines</b-list-group-item>
				  <b-list-group-item>Review old and current sprints </b-list-group-item>
				  <b-list-group-item>Edit profiles and view others</b-list-group-item>
  				  <b-list-group-item>Invite members , assign roless</b-list-group-item>
				  <b-list-group-item>Create Epics , Tasks , Stories</b-list-group-item>
				</b-list-group>
			</b-col>
			<b-col>
				<iframe class="stamp" src="https://www.youtube.com/embed/tgbNymZ7vqY" width="740" height="500" style="float: right" allowfullscreen></iframe>
			</b-col>
		</b-row>
	    </b-container>

	    <b-container class="About Us" id="about" style="background-color: #F5F5FB;" fluid>
	    	<b-row align-h="center">
				<h2 style="font-family:Courier New;"> About Us </h2>
			</b-row>
			<b-row>
				<p></p>
			</b-row>
		    <b-row>
				<b-card-group deck>
			        <b-card title="Yolanda K"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
			            	Java/SQL/Spring , back end developer
			            </p>
			        </b-card>
			        <b-card title="Orestis G"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
			            	Java/SQL/Spring , back end developer
			            </p>
			        </b-card>
			   		<b-card title="Dhmhtra M"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
							Java/Scrum Master , back end developer
						</p>
			        </b-card>
			        <b-card title="Markos V"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
							Vue JS/Javascript/HTML/CSS  , front end developer
			            </p>
			        </b-card>
			        <b-card title="Vagelhs G"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
							Vue JS/Javascript/HTML/CSS  , front end developer
			            </p>
			        </b-card>
			        <b-card title="Kostas Kotronis"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
							HTML/CSS/Javascript Developer , front end
			            </p>
			        </b-card>
			        <b-card title="Vagelhs K"
			                img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRb0XtpVL_8yPpoug8pWRK5VIepB9xP2or2p1Y4dX0iYP8R97gQwA"
			                img-alt="Img"
			                img-top>
			            <p class="card-text" style="font-style: italic;">
							HTML/CSS/Javascript Developer , front end
			            </p>
			        </b-card>
		   		 </b-card-group>
			</b-row>
			<b-row>
				<p></p>
			</b-row>
			<b-row align-h="center">
				<p class="description">
					- We are a team of junior developers , developing our first project , implementing a web cooperative platform for software management. This platform helps teams to implement software projects based on agile methodology (SCRUM) via backlogs, issues, epics and sprints.
				</p>
			</b-row>
			
	       	<b-container class="bottom" style="background-color:#444545;" fluid>
				<b-row align-h="center">
		        	<ul>
		            	<li><a class="active" href="#home">Home</a></li>
		            	<li><a href="#features">Features</a></li>
		            	<li><a href="#about">About Us</a></li>
		        	</ul>
		        </b-row>
		        <b-row align-h="center">
		        	<ul>
	            		<li style="font-size: 13px"><a class="active" href="#terms"> Terms of policy</a></li>
	            		<li style="font-size: 13px"><a href="#privacy"> Privacy policy</a></li>
	        		</ul>
		    	</b-row>
		    </b-container>
	    </b-container>
	</b-container>    
</template>

<script>import axios from 'axios'
export default {
  data() {
    return {
    }
  },
  methods: {
    gotoSignIn() {
      this.$router.push({path: '/signin'});
    },
  }
}
</script>

<style scoped>
.Title{
  position: absolute;
  top: 0;
  left: 0;
}

.Home{
	position: absolute;
	background-color: #f9ebe0; 
	width: 100vw;
	left: 0;
}
.wordcloud{
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 54%;
}

.Features{
  position: absolute;
  top:457%;
  left:0;	
}

.stamp {
	border-style: double;
	border-color: grey;
	border-width: 4px;
}

.About{
  position: absolute;
  top:865%;
  left:4px;		
}

.devs{
 	height: 50%;
 	width: 50%;	
}

.description{
	font-family: "Lucida Console", Monaco, monospace;
	font-size: 25px;
	margin-top: .34em; color: #666;
	color: black;
	font-style: italic;
}

.bottom{
	position: absolute;
 	left:0%;
 	width: 100%;			
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #444545;
  text-align: center;
}

li {
  display: inline-block;
  margin-right: 10px;
}

li a {
  display: inline-block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  margin-right: 10px;
}

li a:visited{
  color: #f9ebe0
}

li a:hover {
  color: #f9ebe0;
}

</style>
