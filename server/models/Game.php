<?php
  class Game {
    private $conn;
    private $table = 'games';

    public $id;
    public $game_title;
    public $white_player;
    public $black_player;
    public $game_state;
    public $initial_fen;

    public function __construct($db) {
      $this->conn = $db;
    }

    public function read() {
      $query = 
        'SELECT
            "id",
            "game_title",
            "game_state"
        FROM ' . $this->table;

      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }

  public function read_game(){
    $query = 
        'SELECT
            id,
            game_title
        FROM ' . $this->table .
        'WHERE id = id
        LIMIT 0,1';

      $stmt = $this->conn->prepare($query);
      $stmt -> bindParam(':id', $this->id);
      $stmt -> execute();

      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      $this->id = $row['id'];
      $this->game_title = $row['game_title'];
  }

  public function create() {
    $query = 
        'INSERT INTO ' . $this->table . 
        'SET
            game_title = :game_title';

  $stmt = $this->conn->prepare($query);

  $this->game_title = htmlspecialchars(strip_tags($this->game_title));
  $this->white_player = htmlspecialchars(strip_tags($this->white_player));
  $this->black_player = htmlspecialchars(strip_tags($this->black_player));

  $stmt-> bindParam(':name', $this->name);

  if($stmt->execute()) {
    return true;
  }

  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  public function update() {
    $query = 'UPDATE ' .$this->table . 
    'SET
      name = :name
      WHERE
      id = :id';

  $stmt = $this->conn->prepare($query);

  $this->name = htmlspecialchars(strip_tags($this->name));
  $this->id = htmlspecialchars(strip_tags($this->id));

  $stmt-> bindParam(':name', $this->name);
  $stmt-> bindParam(':id', $this->id);

  if($stmt->execute()) {
    return true;
  }

  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  public function delete() {
    $query = 'DELETE FROM ' . $this->table . ' WHERE id = :id';

    $stmt = $this->conn->prepare($query);

    $this->id = htmlspecialchars(strip_tags($this->id));

    $stmt-> bindParam(':id', $this->id);

    if($stmt->execute()) {
      return true;
    }

    printf("Error: $s.\n", $stmt->error);

    return false;
    }
  }