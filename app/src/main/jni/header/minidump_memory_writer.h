#ifndef MINIDUMP_MEMORY_WRITER__H_
#define MINIDUMP_MEMORY_WRITER__H_


#include <android/log.h>

//#include "google_breakpad/common/minidump_format.h"
#include "client/minidump_file_writer.h"


namespace google_breakpad {


class minidump_memory_writer : public MinidumpFileWriter
{
	private:
		MDRVA* Memory_Address;

	private :

	public:
		minidump_memory_writer();
		~minidump_memory_writer();

		  // Open |path| as the destination of the minidump data.  Any existing file
		  // will be overwritten.
		  // Return true on success, or false on failure.
		  bool Open(const char *path);

		  // Sets the file descriptor |file| as the destination of the minidump data.
		  // Can be used as an alternative to Open() when a file descriptor is
		  // available.
		  // Note that |fd| is not closed when the instance of MinidumpFileWriter is
		  // destroyed.
		  void SetFile(const int file);

		  // Close the current file (that was either created when Open was called, or
		  // specified with SetFile).
		  // Return true on success, or false on failure.
		  bool Close();

		  // Write |size| bytes starting at |src| into the current position.
		  // Return true on success and set |output| to position, or false on failure
		  bool WriteMemory(const void *src, size_t size, MDMemoryDescriptor *output);

		  // Copies |size| bytes from |src| to |position|
		  // Return true on success, or false on failure
		  bool Copy(MDRVA position, const void *src, ssize_t size);
};

}
#endif
