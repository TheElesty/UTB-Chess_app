<?php 
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../db/Database.php';
  include_once '../../models/Game.php';

  $database = new Database();
  $db = $database->connect();

  $game = new Game($db);

  $result = $game->read();

  if($result->rowCount() > 0) {
        $data = array();
        $data['data'] = array();

        while($row = $result->fetch(PDO::FETCH_ASSOC)) {
          extract($row);

          $item = array(
            'id' => $id,
            'game_title' => $game_title,
            'game_state' => $game_state
          );

          array_push($data['data'], $item);
        }

        echo json_encode($data);

  } else {
        echo json_encode(
          array('message' => 'No Categories Found')
        );
  }