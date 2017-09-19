<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

	 public function index()	{
 		$data['heading']= "404 Page Not Found";
 		$data['message']=" <p>The page you requested was not found.</p>";
 		$this->load->view('errors/html/error_404',$data);
 	}
}
