#include "minidump_memory_writer.h"

namespace google_breakpad {

minidump_memory_writer::minidump_memory_writer()
:MinidumpFileWriter(),Memory_Address(0)
{

}
minidump_memory_writer::~minidump_memory_writer()
{
	Close();
}
bool minidump_memory_writer::Open(const char *path)
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "Memory_Writer_OPEN");
	Memory_Address = (MDRVA*)malloc(1024*1024); // 1M당!!!
	return true;
}

void minidump_memory_writer::SetFile(const int file)
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "SETFILE 언제들오나 보자");
}
bool minidump_memory_writer::Close()
{
	//여기서 옮기는 걸로하자 (메모리 복사)static 영역에 옹키?
	free(Memory_Address);
	Memory_Address = NULL;
	return true;
}
bool minidump_memory_writer::WriteMemory(const void *src, size_t size, MDMemoryDescriptor *output)
{
	//__android_log_print(ANDROID_LOG_DEBUG, "URQAnative", "WriteMemory 들어오나?");

	memcpy(Memory_Address + position_ , src, size);
	position_ += size;
	return true;
}

bool minidump_memory_writer::Copy(MDRVA position, const void *src, ssize_t size)
{
	memcpy(Memory_Address + position, src, size);
	position_ = position;
	return true;
}
}
