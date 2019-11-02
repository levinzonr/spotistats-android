package cz.levinzonr.spoton.models.musicxmatch

data class MusicXMatchResponse<B>(
        val message: Header,
        val body: B
)