package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.NoteCommand;
import guru.springframework.model.Note;
import lombok.Synchronized;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand>{

    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note source) {
        if (source == null) {
            return null;
        }

        final NoteCommand notesCommand = new NoteCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNote(source.getRecipeNote());
        return notesCommand;
    }
}
