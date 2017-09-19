<?php
require APPPATH . '/libraries/REST_Controller.php';
class Dcr extends REST_Controller {
  function __construct($config = 'rest') {
      parent::__construct($config);
  }
  function index_get(){
    if($this->get("One")){// ambil satu data dcr
      $id = array("id_dcr"=>$this->get("One"));
      $this->db->where($id);
      $dcr = $this->db->get('tb_dcr');
      if($dcr->num_rows()>0){
        $data = array();
        foreach ($dcr->result() as $key => $value) {
          $data['status']="sukses";
          $data['id_user']=$value->id_user;
          $data['id_dcr']=$value->id_dcr;
          $data['nama_dcr']=$value->nama_dcr;
          $data['alamat_dcr']=$value->alamat_dcr;
          $data['foto_dcr']=$value->foto_dcr;
          $data['lat']=$value->lat;
          $data['lng']=$value->lng;
        }
        $this->response(array($data), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else if($this->get("getBy")){// get by id user
      $id = array("id_user"=>$this->get("getBy"));
      $this->db->where($id);
      $dcr = $this->db->get('tb_dcr');
      if($dcr->num_rows()>0){
        $data = array();
        foreach ($dcr->result() as $key => $value) {
          $data['status']="sukses";
          $data['id_user']=$value->id_user;
          $data['id_dcr']=$value->id_dcr;
          $data['nama_dcr']=$value->nama_dcr;
          $data['alamat_dcr']=$value->alamat_dcr;
          $data['foto_dcr']=$value->foto_dcr;
          $data['lat']=$value->lat;
          $data['lng']=$value->lng;
        }
        $this->response(array($data), 200);
      }
      else{
        $data = array("status"=>"data_kosong");
        $this->response(array($data), 200);
      }
    }
    else{
      $data = array("status"=>"data_kosong");
      $this->response(array($data), 200);
    }
  }
  function index_post(){
    if($this->post("updateGambar")){
      $name = uplodGambar();
      if($name != FALSE){
        $gambar = array("foto_dcr"=>$name);
        $this->db->where('id_dcr', $this->post("id_dcr"));
        $update = $this->db->update('tb_dcr', $gambar);
        if ($update) {
          $respon = array("status"=>"sukses");
          $this->response(array($respon), 200);
        } else {
          $respon = array("status"=>"gagal");
          $this->response(array($respon), 200);
        }
      }
      else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else if($this->post("dcr_baru")){
      if($this->post("image")){
        $image = $this->post("image");
        $date   = date('Ymd His');
        $name   = str_replace(" ","_",$date);
        $path = "img/".$name.".jpg";
        if(file_put_contents($path,base64_decode($image))){
          $data = array(
            "id_user"=>$this->post("id_user"),
            "nama_dcr"=>$this->post("nama_dcr"),
            "alamat_dcr"=>$this->post("alamat_dcr"),
            "foto_dcr"=>$name.".jpg"
          );
        }
      }
      else{
        $data = array(
          "id_user"=>$this->post("id_user"),
          "nama_dcr"=>$this->post("nama_dcr"),
          "alamat_dcr"=>$this->post("alamat_dcr"),
          "foto_dcr"=>"no_img.jpg"
        );
      }
      $simpan = $this->db->insert("tb_dcr",$data);
      if($simpan){
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      }
      else{
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
  }
  function index_put(){
    if($this->put("latLong")){
      $data = array(
        "lat"=>$this->put("lat"),
        "lng"=>$this->put("lng")
      );
      $this->db->where('id_dcr', $this->put("id_dcr"));
      $update = $this->db->update('tb_dcr', $data);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
    else if($this->put("editDcr")){
      $image = $this->put("image");
      if($image){
        $date   = date('Ymd His');
        $name   = str_replace(" ","_",$date);
        $path = "img/".$name.".jpg";
        if(file_put_contents($path,base64_decode($image))){
          $data = array(
            "nama_dcr"=>$this->put("nama_dcr"),
            "alamat_dcr"=>$this->put("alamat_dcr"),
            "foto_dcr"=>$name.".jpg"
          );
        }
        else{
          $data = array(
            "nama_dcr"=>$this->put("nama_dcr"),
            "alamat_dcr"=>$this->put("alamat_dcr")
          );
        }
      }
      else{
        $data = array(
          "nama_dcr"=>$this->put("nama_dcr"),
          "alamat_dcr"=>$this->put("alamat_dcr")
        );
      }

      $this->db->where('id_dcr', $this->put("id_dcr"));
      $update = $this->db->update('tb_dcr', $data);
      if ($update) {
        $respon = array("status"=>"sukses");
        $this->response(array($respon), 200);
      } else {
        $respon = array("status"=>"gagal");
        $this->response(array($respon), 200);
      }
    }
  }
}
