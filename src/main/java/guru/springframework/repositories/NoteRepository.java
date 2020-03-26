package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.model.Note;

public interface NoteRepository extends CrudRepository<Note, Long>{

}
